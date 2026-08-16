package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.restaurant.inventory.model.Roles;

//@Repository removed the annotationsince its notncessary
public interface RolesRepository extends JpaRepository<Roles,Integer>{

}
