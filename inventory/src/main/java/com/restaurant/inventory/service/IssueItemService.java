package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.IsssueItem;
import com.restaurant.inventory.repository.IssueItemRepository;

@Service
public class IssueItemService {

    @Autowired
    private IssueItemRepository issueItemRepository;

    public void issueItem(IsssueItem isssueItem){
        issueItemRepository.save(isssueItem);
    }

    public List<IsssueItem> getAllissuedItems(){
        return issueItemRepository.findAll();
    }

    public IsssueItem findissuedItembyID(Long issueID){
        return issueItemRepository.findById(issueID)
        .orElseThrow(() -> new RuntimeException("Item not found with ID: " + issueID)); 
    }

    public void updateIssuedItem(IsssueItem isssueItem){
        issueItemRepository.save(isssueItem);
    }

    public void deleteIssuedItemByID(Long issueID){
        issueItemRepository.deleteById(issueID);
    }

}
