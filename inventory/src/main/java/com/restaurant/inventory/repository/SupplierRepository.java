package com.restaurant.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

}
