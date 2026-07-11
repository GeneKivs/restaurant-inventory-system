package com.restaurant.inventory.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.inventory.model.ReceivedItem;

public interface ReceivedItemRepositptory extends JpaRepository<ReceivedItem, Long> {
    List<ReceivedItem> findByExpiryDateBefore(LocalDate expiryDate);

    List<ReceivedItem> findByItem_itemIDOrderByReceivedDateAsc(int itemID);

    List<ReceivedItem> findByExpiryDate(LocalDate date);

    List<ReceivedItem> findByExpiryDateBetween(LocalDate startDate, LocalDate endingDate);

    List<ReceivedItem> findByReceivedDateBetween(LocalDate starDate, LocalDate endDate);

    @Query("SELECT SUM(r.remainingQuantity) FROM ReceivedItem r WHERE r.item.itemID = :itemID")
double sumRemainingQuantityByItemID(@Param("itemID") int itemID);

}
