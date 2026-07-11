package com.restaurant.inventory.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.User;
import com.restaurant.inventory.repository.UserRepository;
import com.restaurant.inventory.utils.PasswordGenerator;


@Service
public class ForgotPsswordService {
    @Autowired
    private  UserRepository userRepository;

   

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailNotificationService emailService;
    
   public boolean resetPassword(String email){
    Optional<User> optionaluser = userRepository.findByEmail(email);

    if(!optionaluser.isPresent()){
        return false;
    }
    User user = optionaluser.get();
    String newPassword = PasswordGenerator.generateRandomPassword();
    String encodedePassword = passwordEncoder.encode(newPassword);

    user.setPassword(encodedePassword);
    userRepository.save(user);

    
    
    emailService.sendResetPasswordEmail(user.getEmail(), user.getUserName(), newPassword);
    
    return true;
   }

    

}
