package com.restaurant.inventory.service;

import java.time.LocalDate;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;


import jakarta.mail.internet.MimeMessage;
 @Service
public class EmailNotificationService {
    
    private JavaMailSender mailSender;


    public EmailNotificationService(JavaMailSender mailSender){
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
    
    
    public void sendLowStockAlert(String email, String itemName, double quantity, double reorderLevel) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
            helper.setTo(email);
            helper.setSubject("Low Stock Alert: " + itemName);
            helper.setText(
                "Attention Inventory Manager,\n\n" +
                "The stock level for the following item is below the reorder threshold:\n\n" +
                "Item: " + itemName + "\n" +
                "Current Quantity: " + quantity + "\n" +
                "Reorder Level: " + reorderLevel + "\n\n" +
                "Please take the necessary action.\n\n" +
                "Regards,\n" +
                "Restaurant Inventory System"
            );
    
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send low stock alert email.");
        }
    }

    public void sendResetPasswordEmail(String email, String userName, String newPassword) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
    
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText(
                "Hello " + userName + ",\n\n" +
                "Your password has been reset as per your request.\n\n" +
                "New Password: " + newPassword + "\n\n" +
                "Please log in using this new password and change it immediately for your security.\n\n" +
                "Regards,\nRestaurant Inventory System"
            );
    
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send reset password email.");
        }
    }
    
    public void sendExpiryAlert(String email, String itemName, LocalDate expiryDate, double quantity, boolean isExpired) {
    try {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(email);

        if (isExpired) {
            helper.setSubject("Expired Item Alert: " + itemName);
            helper.setText(
                "Hello Inventory Manager,\n\n" +
                "The following item has expired:\n\n" +
                "Item: " + itemName + "\n" +
                "Expired On: " + expiryDate + "\n" +
                "Quantity Moved to Expired Stock: " + quantity + "\n\n" +
                "Please take the necessary action.\n\n" +
                "Regards,\nRestaurant Inventory System"
            );

            
        } else {
            helper.setSubject("Upcoming Expiry Alert: " + itemName);
            helper.setText(
                "Hello Inventory Manager,\n\n" +
                "The following item is expiring soon:\n\n" +
                "Item: " + itemName + "\n" +
                "Expiry Date: " + expiryDate + "\n" +
                "Remaining Quantity: " + quantity + "\n\n" +
                "Please plan accordingly.\n\n" +
                "Regards,\nRestaurant Inventory System"
            );

            
        }

        mailSender.send(message);

    } catch (Exception e) {
       
        throw new RuntimeException("Failed to send expiry alert email.", e);
    }
}

    

}
