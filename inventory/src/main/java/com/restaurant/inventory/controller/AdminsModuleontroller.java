package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.inventory.model.User;
import com.restaurant.inventory.service.UserService;

@Controller
public class AdminsModuleontroller { 

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String showAdminsForm(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);

        return "adminform";
    }

}
