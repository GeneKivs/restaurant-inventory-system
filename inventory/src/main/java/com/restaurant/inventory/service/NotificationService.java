package com.restaurant.inventory.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Notification;
import com.restaurant.inventory.model.ReceivedItem;

import com.restaurant.inventory.model.User;
import com.restaurant.inventory.repository.ItemRepository;
import com.restaurant.inventory.repository.NotificationRepository;
import com.restaurant.inventory.repository.ReceivedItemRepositptory;


import jakarta.persistence.EntityNotFoundException;

@Service
public class NotificationService {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private ItemRepository itemRepository;

   

    

    public static final Logger logger = LoggerFactory.getLogger(NotificationService.class);


   

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ReceivedItemRepositptory receivedItemRepositptory;

    
    //create notifictaion
    public void createNotification(Notification notification){
        notificationRepository.save(notification);
    } 
    //get all notifications
    public List<Notification> getALLNotifications(){
        return notificationRepository.findAll();
    }

    //get all unread notifications
    public List<Notification> getUnreadNotifications(){
        return notificationRepository.findByIsreadFalse();
    }

    public void maskRead(Long notificationID){
        Optional<Notification> notificationOpt = notificationRepository.findById(notificationID);
         if (notificationOpt.isPresent()) {
        Notification notification = notificationOpt.get();
        notification.setIsread(true);  // Assuming there's a boolean field isRead
        notificationRepository.save(notification);
    } else {
        throw new EntityNotFoundException("Notification not found with ID: " + notificationID);
    }


    }

    public void notifypurchaseorder(Long orderID, String itemName){
        Notification notification = new Notification();
        notification.setType("PurchaseOrder");
        notification.setMessage("A new purchase order (ID: " + orderID + ") has been generated for item: " + itemName);
        notificationRepository.save(notification);
    }

    

    
   @Scheduled(fixedRate = 60000)
public void checkAndNotifyLowStock() {
    List<Item> items = itemService.getAllItems();

    for (Item item : items) {
        if (item.getQuantity() <= item.getReorderlevel()) {
            boolean exists = notificationRepository.existsByTypeAndMessage(
                "Lowstock", 
                "Stock for " + item.getItemName() + " is below the threshold."
            );

            if (!exists) {
                Notification notification = new Notification();
                notification.setType("Lowstock");
                notification.setMessage("Stock for " + item.getItemName() + " is below the threshold.");
                notificationRepository.save(notification);

                List<User> managers = userService.findUserByRole("Inventory Manager");
                for (User manager : managers) {
                    if (manager.getEmail() != null && !manager.getEmail().isEmpty()) {
                        emailNotificationService.sendLowStockAlert(
                            manager.getEmail(),
                            item.getItemName(),
                            item.getQuantity(),
                            item.getReorderlevel()
                        );
                    }
                }
            }
        }
    }
}



    @Scheduled(cron = "0 0 0 * * ?") // Runs this method daily at 12:00 AM
    @Transactional
    public void checkforEXpiringItem(){
        LocalDate today = LocalDate.now();
        LocalDate alertDate = LocalDate.now().plusDays(3);

        List<User> inventoryManagers = userService.findUserByRole("Inventory Manager");

        //check for items that have expired today
        List<ReceivedItem> expiredItems = receivedItemRepositptory.findByExpiryDate(today);
        for(ReceivedItem receivedItem: expiredItems){
            if(receivedItem.getRemainingQuantity()> 0){
                // move to expiry stock
                double  expiredQuantity = receivedItem.getRemainingQuantity();

                //update expiry stock in  recieved items
                receivedItem.setExpiredstock(receivedItem.getExpiredstock() + expiredQuantity);

                //reduce main quantity in item table
                Item item = receivedItem.getItem();
                item.setQuantity(item.getQuantity() - expiredQuantity);

                //set received quantity to 0
                receivedItem.setRemainingQuantity(0);;

                //save the changes
                itemRepository.save(item);
                receivedItemRepositptory.save(receivedItem);

                //create message
                String message = "The Item " + item.getItemName() + " has expired on " + today + ". " + expiredQuantity + " units moved to epiry stock.";

                if(!notificationRepository.existsByTypeAndMessage("ExpiryAlert", message)){

                    Notification notification = new Notification();
                    notification.setType("ExpiryAlert");
                    notification.setMessage(message);
                    notificationRepository.save(notification);

                    logger.info("Expiry processed for " + item.getItemName() + ", " + expiredQuantity + "  units  expired");

                    for(User manager : inventoryManagers){
                        emailNotificationService.sendExpiryAlert(
                            manager.getEmail(),
                            item.getItemName(),
                            today,
                            expiredQuantity,
                            true
                        );
                    }
                }
            }
        }


         List<ReceivedItem> expirySoon = receivedItemRepositptory.findByExpiryDateBetween(today.plusDays(1),alertDate);

         for(ReceivedItem receivedItem : expirySoon){
            if (receivedItem.getReceiveedquantity() > 0) {
                Boolean existing = notificationRepository.existsByTypeAndMessage("ExpiryAlert", 
                "The Item " + receivedItem.getItem().getItemName() + "is expiring on " + receivedItem.getExpiryDate());

                if(!existing){
                    Notification notification = new Notification();
                    notification.setType("ExpiryAlert");
                    notification.setMessage("The item" + receivedItem.getItem().getItemName() + "is expiring on " + receivedItem.getExpiryDate());
                    notificationRepository.save(notification);

                    logger.info("Expiry Noticiation created for " + receivedItem.getItem().getItemName());
                    
                    for(User manager : inventoryManagers){
                        emailNotificationService.sendExpiryAlert(
                            manager.getEmail(),
                            receivedItem.getItem().getItemName(),
                            receivedItem.getExpiryDate(),
                            receivedItem.getRemainingQuantity(),
                            false
                        );
                    }
                }else{
                    logger.info("Notification already exists for " + receivedItem.getItem().getItemName());
                
            }
            
                }
         }
    }


     

    
}
