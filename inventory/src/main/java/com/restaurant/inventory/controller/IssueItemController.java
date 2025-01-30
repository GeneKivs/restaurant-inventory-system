package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import com.restaurant.inventory.model.IsssueItem;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.IssueItemService;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.UnitService;

@Controller
public class IssueItemController {

    @Autowired
    private UnitService unitService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private IssueItemService issueItemService;

    @GetMapping("issuedItem")
    public String showIssueForm(Model model){
        IsssueItem isssueItem = new IsssueItem();
        model.addAttribute("isssueItem", isssueItem);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);


        return "issueform";

    }
    
    @PostMapping("/saveIssuedItem")
    public String saveIssuedItem(@ModelAttribute("isssuedItem") IsssueItem isssueItem,Model model){
        Integer itemID = isssueItem.getItem().getItemID();
        Item item = itemService.findItemById(itemID);

        if (item.getQuantity() < isssueItem.getIssuedQuantity()) {
            throw new RuntimeException("Insufficient stock!");
        }
    

        double newquantity = item.getQuantity() - isssueItem.getIssuedQuantity();
        item.setQuantity(newquantity);

        itemService.defineItem(item);
        issueItemService.issueItem(isssueItem);
        return "redirect:/issuedItem";
    }

}
