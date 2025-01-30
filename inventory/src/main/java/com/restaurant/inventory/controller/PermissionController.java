package com.restaurant.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.inventory.model.Permission;
import com.restaurant.inventory.service.PermissionService;

@Controller
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping("/permission")
    public String showPermissionForm(Model model){
        Permission permission = new Permission();
        model.addAttribute("permision", permission);
        return "permissionform";
    }

    @PostMapping("/savePermission")
    public String savePermission(@ModelAttribute("permission") Permission permission){
        permissionService.definePermision(permission);
        return "redirect:/permission";
    }


}
