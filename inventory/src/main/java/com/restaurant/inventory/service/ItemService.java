package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.repository.ItemRepository;

@Service
public class ItemService {
    
    @Autowired
    private ItemRepository itemRepository;

    public void defineItem(Item item){
        itemRepository.save(item);
    }

    public List<Item> getAllItems(){
        return itemRepository.findAll();
    }

    public Item findItemById(int itemID){
        return itemRepository.findById(itemID)
                .orElseThrow(() -> new RuntimeException("Item not found with ID: " + itemID));
    }

    //update the item
    public void updateItem(Item item){
        itemRepository.save(item);
    }

    //delete the item by id
    public void deleteItemById(int itemID){
        itemRepository.deleteById(itemID);
    }

}
