package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.ReceivedItem;

public interface ReceivedItemRepositptory extends JpaRepository<ReceivedItem, Long> {

}
