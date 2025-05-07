package com.example.shopping_cart.util;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {

    @Autowired
    private JavaMailSender mailSender;

    public Boolean sendMail(String url, String email) throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            
            helper.setFrom("noreply@shopcart.com", "Shop Cart Support");
            helper.setTo(email);
            
            String subject = "Password Reset Link";
            String content = "<p>Hello,</p>"
                           + "<p>You have requested to reset your password.</p>"
                           + "<p>Click the link below to reset your password:</p>"
                           + "<p><a href=\"" + url + "\">Reset Password</a></p>"
                           + "<p>Ignore this email if you have not requested password reset.</p>";
            
            helper.setSubject(subject);
            helper.setText(content, true);
            
            mailSender.send(message);
            return true;
        } catch (MessagingException | UnsupportedEncodingException | MailException e) {
            return false;
        }
    }
    
    public String generateUrl(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}