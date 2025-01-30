package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.IsssueItem;

public interface IssueItemRepository extends JpaRepository<IsssueItem,Long>{

}
