package com.restaurant.inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.IsssueItem;

public interface IssueItemRepository extends JpaRepository<IsssueItem,Long>{

    List<IsssueItem> findByIssueDateBetween(LocalDate startDate, LocalDate endDate);

}
