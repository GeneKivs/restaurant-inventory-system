package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Permission;
import com.restaurant.inventory.repository.PermissionRepository;

@Service // Marks this class as a service component in Spring
public class PermissionService {

    @Autowired // Automatically injects an instance of PermissionRepository
    private PermissionRepository permissionRepository;

    // Method to define and save a new permission
    public void definePermision(Permission permission){
        permissionRepository.save(permission);
    }

    // Method to retrieve all permissions from the database
    public List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }

    // Method to find a specific permission by its ID
    public Permission findPermissionByID(int permissionID){
        return permissionRepository.findById(permissionID)
        .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + permissionID));
    }
    
    // Method to update an existing permission
    public void updatePermission(Permission permission){
        permissionRepository.save(permission);
    }

    // Method to delete a permission by its ID
    public void deletePermissionByID(int permissionID){
        permissionRepository.deleteById(permissionID);
    }
}