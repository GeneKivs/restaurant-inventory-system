package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.User;
import com.restaurant.inventory.repository.UserRepository;
import com.restaurant.inventory.utils.PasswordGenerator;

@Service
public class UserService { 
    
   @Autowired
    private NotificationService notificationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void defineuser(User user){
        //set email to userName
        user.setUserName(user.getEmail());

        //GenerateRandom Password
         String rawPassword = PasswordGenerator.generateRandomPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(hashedPassword);
        userRepository.save(user);

       notificationService.sendCredentials(user.getEmail(),  user.getUserName(), rawPassword);


    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User findUserByID(int userID){
        return userRepository.findById(userID)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
    }

    public void updateUser(User user){
        userRepository.save(user);
    }

    public void deleteUserByID(int userID){
        userRepository.deleteById(userID);
    }


}
