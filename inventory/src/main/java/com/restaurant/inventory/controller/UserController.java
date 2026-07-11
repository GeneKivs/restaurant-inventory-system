package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.model.Roles;
import com.restaurant.inventory.model.User;
import com.restaurant.inventory.service.RolesService;
import com.restaurant.inventory.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RolesService rolesService;

    @GetMapping("/userRegistration")
    public String showUserregform(Model model){
        User user = new User();
        model.addAttribute("user", user);

        List<Roles> roles = rolesService.getAllRoles();
        model.addAttribute("roles", roles);
       


        return "userregistration";
    }

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User user,RedirectAttributes redirectAttributes){
        userService.defineuser(user);
        redirectAttributes.addFlashAttribute("successMessage", "user registered successfully and password send to the email.");
        return "redirect:/userRegistration";
    }

    @GetMapping("/updateUser/{userID}")
    public String showUserEditForm(@PathVariable("userID")int userID, Model model){
        User user = userService.findUserByID(userID);
        model.addAttribute("user", user);

        List<Roles> roles = rolesService.getAllRoles();
        model.addAttribute("roles", roles);
        return "useredit";
    }

    @PostMapping("/updateUser")
    public String updateUser(@ModelAttribute("user")User user,RedirectAttributes redirectAttributes){
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "user updated successfully.");
        return "redirect:/admin";
    }


    @PostMapping("/deleteUser/{userID}")
    public String deleteUser(@PathVariable("userID") int userID, RedirectAttributes redirectAttributes) {
        userService.deleteUserByID(userID);
        redirectAttributes.addFlashAttribute("successMessage", "User has been set to inactive (soft deleted).");
        return "redirect:/admin";
    }

}
