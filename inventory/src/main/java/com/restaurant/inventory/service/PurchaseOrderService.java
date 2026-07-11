package com.restaurant.inventory.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.repository.PurchaseOrderRepository;

@Service // Marks this class as a service component in Spring
public class PurchaseOrderService {
    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderService.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private ItemService itemService;

    @Autowired // Automatically injects an instance of PurchaseOrderRepository
    private PurchaseOrderRepository purchaseOrderRepository;

    // Method to create and save a new purchase order
    public PurchaseOrder createOrder(PurchaseOrder purchaseOrder){
        return purchaseOrderRepository.save(purchaseOrder);
    }

    // Method to retrieve all purchase orders from the database
    public List<PurchaseOrder> getAllOrders(){
        return purchaseOrderRepository.findAll();
    }

    // Method to find a specific purchase order by its ID
    public PurchaseOrder findOrderByID(Long orderID){
        return purchaseOrderRepository.findById(orderID)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderID));
    }

    // Method to update an existing purchase order
    public void updateOrder(PurchaseOrder purchaseOrder){
        purchaseOrderRepository.save(purchaseOrder);
    }

    // Method to delete a purchase order by its ID
    public void deleteOrderById(Long orderID){
        purchaseOrderRepository.deleteById(orderID);
    }

    // Method to count the number of pending purchase orders
    public long countPendingOrders() {
        return purchaseOrderRepository.countByOrderStatus("PENDING");
    }

    public long countGeneratedOrders(){
        return purchaseOrderRepository.countByOrderStatus("generated");
    }

    // Method to retrieve purchase orders based on a specific item ID
    public List<PurchaseOrder> getOrdersByitemID(int itemID){
        return purchaseOrderRepository.findByItemID(itemID);
    }

    // Method to retrieve pending purchase orders for a specific item ID
    public List<PurchaseOrder> getPendingOrdersByItemID(int itemID) {
        return purchaseOrderRepository.findByItemIDAndStatus(itemID, "Pending");
    }

    public boolean isItemInTransaction(Item item) {
        return purchaseOrderRepository.existsByItem(item);
    } 

    public boolean isSupplierInTransaction(Supplier supplier){
        return purchaseOrderRepository.existsBySupplier(supplier);
    }

    @Scheduled(fixedRate = 60000)
    public void genratePurchaseOrder(){
            List<Item> items= itemService.getAllItems();

            for(Item item : items){
            if(item.getQuantity() <= item.getReorderlevel()){
                PurchaseOrder purchaseOrder = new PurchaseOrder();

                boolean existingOrder = purchaseOrderRepository.existsByItemAndOrderStatusIn(item, Arrays.asList("generated","pending"));
                if(!existingOrder){
                purchaseOrder.setItem(item);
                purchaseOrder.setUnit(item.getUnit());
                purchaseOrder.setOrderStatus("generated");
                purchaseOrder.setOrderDate(LocalDate.now());

                PurchaseOrder savePurchase = purchaseOrderRepository.save(purchaseOrder);

                notificationService.notifypurchaseorder(savePurchase.getOrderID(),savePurchase.getItem().getItemName());
                logger.info("Purchase order generated for item: " + item.getItemName());
            } 
        }
        }

    }
}