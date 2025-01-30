package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurant.inventory.model.Permission;
import com.restaurant.inventory.model.Roles;
import com.restaurant.inventory.service.PermissionService;
import com.restaurant.inventory.service.RolesService;

@Controller
public class RoleController {

    @Autowired
    private RolesService rolesService;

    @Autowired
    private PermissionService permissionService;


    @GetMapping("/roles")
    public String showRoleform(Model model){
        Roles role = new Roles();
        model.addAttribute("role", role);
        model.addAttribute("permissions", permissionService.getAllPermissions());

        return "roleform";
    }

    @PostMapping("/saveRole")
    public String saveRole(@ModelAttribute("role")Roles role,@RequestParam List<Integer> permissionIDs){
        List<Permission> permissions = permissionService.getAllPermissions()
                .stream()
                .filter(p -> permissionIDs.contains(p.getPermissionID()))
                .toList();
        role.setPermissions(permissions);
        rolesService.defineRole(role);
        return "redirect:/roles";
    }

}
