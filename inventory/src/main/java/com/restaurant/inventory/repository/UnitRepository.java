package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Unit;

public interface UnitRepository extends JpaRepository<Unit,Integer> {

}
