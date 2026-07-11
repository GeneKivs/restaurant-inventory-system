package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.restaurant.inventory.model.User;
import com.restaurant.inventory.repository.UserRepository;
import com.restaurant.inventory.utils.PasswordGenerator;

@Service
public class UserService { 
    
   @Autowired
    private EmailNotificationService emailNotificationService;

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

       emailNotificationService.sendCredentials(user.getEmail(),  user.getUserName(), rawPassword);


    }

    public List<User> getAllUsers(){
        return userRepository.findAllActive();
    }

    public User findUserByID(int userID){
        return userRepository.findById(userID)
        .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
    }

     public User findByUsername(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> findUserByRole(String roleName){
        return userRepository.findByRoleName(roleName);
    }

    public void updateUser(User user){
        // Fetch the existing user from the database
        User existingUser = userRepository.findById(user.getUserID())
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + user.getUserID()));

        // Preserve password and username
        user.setPassword(existingUser.getPassword());
        user.setUserName(existingUser.getUserName());

        // Save the updated user
        userRepository.save(user);
    }

    public void deleteUserByID(int userID){
        // Soft delete: set user as inactive
        User user = userRepository.findById(userID)
            .orElseThrow(() -> new RuntimeException("User not found with ID: " + userID));
        user.setActive(false);
        userRepository.save(user);
    }
    public boolean emailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
    
    

}
