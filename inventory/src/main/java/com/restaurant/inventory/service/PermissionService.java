package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Permission;
import com.restaurant.inventory.repository.PermissionRepository;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    public void definePermision(Permission permission){
        permissionRepository.save(permission);
    }

    public List<Permission> getAllPermissions(){
        return permissionRepository.findAll();
    }

    public Permission findPermissionByID(int permissionID){
        return permissionRepository.findById(permissionID)
        .orElseThrow(() -> new RuntimeException("Permission not found with ID: " + permissionID));
    }
    
    public void updatePermission(Permission permission){
        permissionRepository.save(permission);
    }

    public void deletePermissionByID(int permissionID){
        permissionRepository.deleteById(permissionID);
    }


}
