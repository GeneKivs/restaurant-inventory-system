package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Permission;

public interface PermissionRepository extends JpaRepository<Permission,Integer>{

}
