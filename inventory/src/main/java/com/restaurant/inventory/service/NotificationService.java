package com.restaurant.inventory.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {

    private JavaMailSender mailSender;

    public NotificationService(JavaMailSender mailSender){
        this.mailSender = mailSender;

    }
    public void sendCredentials(String email, String username, String password) {
        if (email != null && !email.isEmpty()) {
            sendEmail(email, username, password);
        }
    }

    private void sendEmail(String email, String userName, String password) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Your Account Credentials");
            helper.setText(
                "Welcome to the system! Here are your credentials:\n\n" +
                "Username (Email): " + userName + "\n" +
                "Password: " + password + "\n\n" +
                "Please log in and change your password as soon as possible."
            );

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email.");
        }
    }
}
