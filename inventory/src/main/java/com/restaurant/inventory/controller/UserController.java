package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("saveUser")
    public String saveUser(@ModelAttribute("user") User user){
        userService.defineuser(user);
        return "redirect:/userRegistration";
    }

}
