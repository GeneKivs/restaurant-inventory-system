package com.restaurant.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.model.SupplierLinkage;

public interface SupplierLinkageRepository extends JpaRepository<SupplierLinkage,Integer>{

    @Query("SELECT supplier FROM SupplierLinkage where item.itemID= :itemID")
    List<Supplier> findSuppliersByitemId(@Param("itemID")int itemID);

}
