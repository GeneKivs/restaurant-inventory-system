package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Categories;

public interface CategoryRepository extends JpaRepository<Categories,Integer>{

}
