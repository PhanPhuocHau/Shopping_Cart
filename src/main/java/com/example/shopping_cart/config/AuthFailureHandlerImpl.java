package com.example.shopping_cart.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.example.shopping_cart.model.UserDtls;
import com.example.shopping_cart.repository.UserRepository;
import com.example.shopping_cart.service.UserService;
import com.example.shopping_cart.util.AppConstant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class AuthFailureHandlerImpl extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		String email = request.getParameter("username");

		UserDtls userDtls = userRepository.findByEmail(email);

			if (userDtls != null) {
				if (userDtls.getIsEnable()) {
					if (userDtls.getAccountNonLocked()) {
						if (userDtls.getFailedAttempt() < AppConstant.ATTEMPT_TIME - 1) {
							userService.increaseFailedAttempt(userDtls);
						} else {
							userService.userAccountLock(userDtls);
							exception = new LockedException("Your account is locked !! failed attempt " + AppConstant.ATTEMPT_TIME);
						}
							} else {
								if (userService.unlockAccountTimeExpired(userDtls)) {
									exception = new LockedException("Your account is unlocked !! Please try to login");
								} else {
									exception = new LockedException("your account is Locked !! Please try after sometimes");
								}
							}
							}else{
								exception = new LockedException("your account is inactive");
							}
					} else {
					exception = new LockedException("Email & password invalid");
				}

				super.setDefaultFailureUrl("/signin?error");
				super.onAuthenticationFailure(request, response, exception);
			}
}