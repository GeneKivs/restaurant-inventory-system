package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.ReceivedItem;
import com.restaurant.inventory.repository.ReceivedItemRepositptory;

@Service
public class ReceivedItemService {

    @Autowired
    private ReceivedItemRepositptory receivedItemRepositptory;

    public void additems(ReceivedItem receivedItem){
        receivedItemRepositptory.save(receivedItem);
    }

    public List<ReceivedItem> getALLReceivedItems(){
        return receivedItemRepositptory.findAll();
    }

    public ReceivedItem finnReceivedItembyID(Long receivedID){
        return receivedItemRepositptory.findById(receivedID)
        .orElseThrow(() -> new RuntimeException("Item not found with ID: " + receivedID));
    }

    public void updateReceivedItem(ReceivedItem receivedItem){
        receivedItemRepositptory.save(receivedItem);
    }

    public void deleteTransbyid(Long receivedID){
        receivedItemRepositptory.deleteById(receivedID);
    }


}
