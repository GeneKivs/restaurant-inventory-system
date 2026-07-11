package com.restaurant.inventory.service;

import java.time.LocalDate;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.IsssueItem;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.ReceivedItem;

import com.restaurant.inventory.repository.IssueItemRepository;
import com.restaurant.inventory.repository.ReceivedItemRepositptory;

@Service
public class IssueItemService {
     

    @Autowired
    private ItemService itemService;

    
    @Autowired
    private ReceivedItemRepositptory receivedItemRepositptory;


    @Autowired
    private IssueItemRepository issueItemRepository;


    //saving the transaction in the database
    public void issueItem(IsssueItem isssueItem){
        int itemID = isssueItem.getItem().getItemID();
        double quantityToIssue = isssueItem.getIssuedQuantity();

        List<ReceivedItem> receivedItems = receivedItemRepositptory.findByItem_itemIDOrderByReceivedDateAsc(itemID);

        if(receivedItems.isEmpty()){
            throw new RuntimeException("No stock available for item: " + itemID);

        }

        double remainingQuantity = quantityToIssue;

        for(ReceivedItem receivedItem : receivedItems){
            if(receivedItem.getExpiryDate() != null && receivedItem.getExpiryDate().isBefore(LocalDate.now())){
                continue;
            }

            double availableStock = receivedItem.getRemainingQuantity();

            if(availableStock <= 0){
                continue;
            }

            if(remainingQuantity <= availableStock){
                receivedItem.setRemainingQuantity(receivedItem.getRemainingQuantity()- remainingQuantity);
                remainingQuantity = 0;
            }else{
                remainingQuantity -= availableStock;
                receivedItem.setRemainingQuantity(0);

            }
             receivedItemRepositptory.save(receivedItem);

             if(remainingQuantity == 0){
                break;
             }
        }

        Item item = isssueItem.getItem(); 
double totalRemainingStock = receivedItemRepositptory.sumRemainingQuantityByItemID(itemID);

item.setQuantity(totalRemainingStock);
itemService.updateItem(item);;

        

        issueItemRepository.save(isssueItem);

    }

    //retreiving a list of all the transactions 
    public List<IsssueItem> getAllIssuedItems(){

        return issueItemRepository.findAll();
    }

    //finding an issueitem transaction by id
    public IsssueItem findissuedItembyID(Long issueID){
        return issueItemRepository.findById(issueID)
        .orElseThrow(() -> new RuntimeException("Item not found with ID: " + issueID)); 
    }

    //updating the issueitem transaction
    public void updateIssuedItem(IsssueItem isssueItem){
        issueItemRepository.save(isssueItem);
    }

    //deleting the issueitem transaction by id 
    public void deleteIssuedItemByID(Long issueID){
        issueItemRepository.deleteById(issueID);
    }

    public List<IsssueItem> getIsseudItemsBetwwendates(LocalDate startDate, LocalDate endDate){
        return issueItemRepository.findByIssueDateBetween(startDate, endDate);
    }

}
