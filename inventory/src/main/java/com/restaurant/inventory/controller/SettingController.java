package com.restaurant.inventory.controller;

import java.security.Principal;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.model.User;
import com.restaurant.inventory.service.UserService;

@Controller
public class SettingController {

   /* @Autowired
    private PasswordEncoder passwordEncoder;
     
    @Autowired
    private UserService userService;*/

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public SettingController(UserService userService, PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;

    }

    @GetMapping("/setting")
    public String showSettingform(){
        return "settingform";
    }

    @GetMapping("/profile")
    public String showPriofileSettingform( Model model, Principal principal){
        String userName = principal.getName();
        User user = userService.findByUsername(userName);

        model.addAttribute("user", user);
        return "profilesettingform";
    }

    @GetMapping("/profile/changePassword")
    public String showchangepasswordfrom(){
        return "changepassword";
    }

    @PostMapping("/profile/updatePassword")
     public String changePassword(Model model,
                                   Principal principal,
                                   @RequestParam("newuserName") String newuserName,
                                   @RequestParam("oldPassword") String oldPassword,
                                   @RequestParam("newPassword") String newPassword,
                                    RedirectAttributes redirectAttributes){

           //get the currently looged in user
           String currentUserName = principal.getName();
           User user = userService.findByUsername(currentUserName);
           
           if(user == null){
            redirectAttributes.addFlashAttribute("errorMessage", "usr not found");
            return "redirect:/profile/changePassword";
           }

           //check if the old password is correct
           if(!passwordEncoder.matches(oldPassword, user.getPassword())){
            redirectAttributes.addFlashAttribute("errorMessage", "password does not match");
            return "redirect:/profile/changePassword";
           }

           //update the username if changed 
           if(!newuserName.equals(currentUserName)){
            user.setUserName(newuserName);
           }

           //hash and update the new password
           user.setPassword(passwordEncoder.encode(newPassword));

           //save update user
           userService.updateUser(user);

           redirectAttributes.addFlashAttribute("successMessage", "Password changed successfully");
        return "redirect:/profile";
     }

}
