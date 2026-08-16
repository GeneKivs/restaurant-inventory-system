package com.restaurant.inventory.service;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Roles;
import com.restaurant.inventory.repository.RolesRepository;

@Service
public class RolesService {

    /*@Autowired
    private RolesRepository rolesRepository;*/
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    public void defineRole(Roles role){
        rolesRepository.save(role);
    }

    public List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }

    public Roles findRoleByID(int roleID){
        return rolesRepository.findById(roleID)
        .orElseThrow(() -> new RuntimeException("role not found with ID: " + roleID));
    }

    public void updateRole(Roles role){
        rolesRepository.save(role);
    }

    public void deleteRoleByID(int roleID){
        rolesRepository.deleteById(roleID);
    } 

}
