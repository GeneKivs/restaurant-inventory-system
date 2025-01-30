package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Item;

public interface ItemRepository extends JpaRepository<Item,Integer> {

}
