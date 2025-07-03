package com.backend.property_plug.Services;

import com.backend.property_plug.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${app.base-url}")
    private String baseUrl;

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account Verification");
        message.setText("Dear " + user.getFullName() + ",\n\n" +
                "Please click the following link to verify your account:\n" +
                baseUrl + "/auth/verify?token=" + user.getVerificationToken() + "\n\n" +
                "This link will expire in 24 hours.\n\n" +
                "Best regards,\n" +
                "Your Application Team");

        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset Request");
        message.setText("Dear " + user.getFullName() + ",\n\n" +
                "You have requested to reset your password. Please click the following link:\n" +
                baseUrl + "/auth/reset-password?token=" + user.getResetToken() + "\n\n" +
                "This link will expire in 1 hour.\n\n" +
                "If you did not request this, please ignore this email.\n\n" +
                "Best regards,\n" +
                "Your Application Team");

        mailSender.send(message);
    }
}