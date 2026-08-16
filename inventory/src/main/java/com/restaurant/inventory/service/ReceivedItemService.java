package com.restaurant.inventory.service;

import java.time.LocalDate;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.ReceivedItem;
import com.restaurant.inventory.repository.ReceivedItemRepositptory;

@Service // Marks this class as a service component in Spring boot
public class ReceivedItemService {

    /*@Autowired // Automatically injects an instance of ReceivedItemRepository
    private ReceivedItemRepositptory receivedItemRepositptory;*/

    private final ReceivedItemRepositptory receivedItemRepositptory;

    public ReceivedItemService(ReceivedItemRepositptory receivedItemRepositptory){
        this.receivedItemRepositptory = receivedItemRepositptory;
    }

    // Method to add a new received item to the database
    public void additems(ReceivedItem receivedItem){
        receivedItem.setRemainingQuantity(receivedItem.getReceiveedquantity() - receivedItem.getDamagequantity());
        receivedItemRepositptory.save(receivedItem);
    }

    // Method to retrieve all received items from the database
    public List<ReceivedItem> getALLReceivedItems(){
        return receivedItemRepositptory.findAll();
    }

    // Method to find a specific received item by its ID
    public ReceivedItem finnReceivedItembyID(Long receivedID){
        return receivedItemRepositptory.findById(receivedID)
        .orElseThrow(() -> new RuntimeException("Item not found with ID: " + receivedID));
    }

    // Method to update an existing received item
    public void updateReceivedItem(ReceivedItem receivedItem){
        receivedItemRepositptory.save(receivedItem);
    }

    // Method to delete a received item by its ID
    public void deleteTransbyid(Long receivedID){
        receivedItemRepositptory.deleteById(receivedID);
    }

    public List<ReceivedItem> getReceiveditemsBetweendates(LocalDate startDate, LocalDate endDate){
        return receivedItemRepositptory.findByReceivedDateBetween(startDate,endDate);
    }
}
