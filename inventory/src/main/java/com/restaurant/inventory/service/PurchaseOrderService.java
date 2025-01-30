package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.repository.PurchaseOrderRepository;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public void createOrder(PurchaseOrder purchaseOrder){

        purchaseOrderRepository.save(purchaseOrder);
    }

    public List<PurchaseOrder> getAllOrders(){
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder findOrderByID(Long orderID){
        return purchaseOrderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderID));
    }

    public void updateOrder(PurchaseOrder purchaseOrder){
        purchaseOrderRepository.save(purchaseOrder);
    }

    public void deleteOrderById(Long orderID){
        purchaseOrderRepository.deleteById(orderID);
    }


    public long countPendingOrders() {
        return purchaseOrderRepository.countByOrderStatus("PENDING");
    }

    public List<PurchaseOrder> getOrdersByitemID(int itemID){
        return purchaseOrderRepository.findByItemID(itemID);
    }

    public List<PurchaseOrder> getPendingOrdersByItemID(int itemID) {
        return purchaseOrderRepository.findByItemIDAndStatus(itemID, "Pending");
    }


}
