package com.restaurant.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.inventory.model.PurchaseOrder;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    long countByOrderStatus(String orderStatus);
    
    @Query("SELECT o FROM PurchaseOrder o JOIN o.item i WHERE i.itemID = :itemID")
    List<PurchaseOrder> findByItemID (@Param("itemID")int itemID);

     // New method to find orders by item ID and status
     @Query("SELECT o FROM PurchaseOrder o JOIN o.item i WHERE i.itemID = :itemID AND o.orderStatus = :orderStatus")
     List<PurchaseOrder> findByItemIDAndStatus(@Param("itemID") int itemID, @Param("orderStatus") String orderStatus);
 

}
