package com.restaurant.inventory.controller;


import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.model.IsssueItem;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.IssueItemService;
import com.restaurant.inventory.service.ItemService;



@Controller
public class IssueItemController {


    /*@Autowired
    private ItemService itemService;
    @Autowired
    private IssueItemService issueItemService;*/

    private final ItemService itemService;
    private final IssueItemService issueItemService;

    public IssueItemController(IssueItemService issueItemService, ItemService itemService){
        this.itemService = itemService;
        this.issueItemService = issueItemService;

    }

    @GetMapping("/inventory/issuedItem")
    public String showIssueForm(Model model,@RequestParam( value = "itemID" , required = false)  Integer itemID){
        IsssueItem isssueItem = new IsssueItem();
        model.addAttribute("isssueItem", isssueItem);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        

        
        Unit selectedUnit = null;

        if(itemID != null){
            Item selectedItem = itemService.findItemById(itemID);
            if(selectedItem != null){
                selectedUnit = selectedItem.getUnit();
            }
        }

        model.addAttribute("selectedUnit", selectedUnit);


        return "issueform";

    }
    
    @PostMapping("/saveIssuedItem")
    public String saveIssuedItem(@ModelAttribute("isssuedItem") IsssueItem isssueItem,@RequestParam("unitID") Integer unitID,Model model,RedirectAttributes redirectAttributes){
        Integer itemID = isssueItem.getItem().getItemID();
        Item item = itemService.findItemById(itemID);

        if ( item.getQuantity()<isssueItem.getIssuedQuantity()) {
            redirectAttributes.addFlashAttribute("errorMessage", "The quantity is not in stock.");
            return "redirect:/inventory/issuedItem";
        }
    

        //double newquantity = item.getQuantity() - isssueItem.getIssuedQuantity();
        //item.setQuantity(newquantity);

        if (unitID != null) {
            isssueItem.setUnit(item.getUnit());  // Assuming IsssueItem has a `Unit` field
        }

        
        issueItemService.issueItem(isssueItem);
   
        redirectAttributes.addFlashAttribute("successMessage", "Item issued successfully.");
        return "redirect:/inventory/issuedItem";
    }

    @GetMapping("/inventory/issuelist")
    public String showIssuedList(Model model){
        List<IsssueItem> isssueItems = issueItemService.getAllIssuedItems();
        model.addAttribute("isssueItems", isssueItems);
        return "issuelist";
    }

}
