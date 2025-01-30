package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

}
