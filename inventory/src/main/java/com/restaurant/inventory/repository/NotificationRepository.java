package com.restaurant.inventory.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restaurant.inventory.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification,Long>{
    List<Notification> findByIsreadFalse();
    boolean existsByTypeAndMessage(String type, String message);

}
