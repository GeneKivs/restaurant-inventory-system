package com.restaurant.inventory.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.model.ReceivedItem;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.PurchaseOrderService;
import com.restaurant.inventory.service.ReceivedItemService;

import com.restaurant.inventory.service.UnitService;

@Controller
public class ReceivedItemController {



    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private ReceivedItemService receivedItemService;



    @GetMapping("/receivedItem")
    public String showReceivedItemForm(Model model,@RequestParam( value = "itemID" , required = false)  Integer itemID){
        ReceivedItem receivedItem = new ReceivedItem();
        model.addAttribute("receivedItem", receivedItem);

       model.addAttribute("filterOrders", new ArrayList<>()); 
         List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);

        List<PurchaseOrder> orders = new ArrayList<>();
        String selectedItemName = null;

    if (itemID != null) {
        orders = purchaseOrderService.getPendingOrdersByItemID(itemID);
        Item selectedItem = itemService.findItemById(itemID); // Fetch the selected item
        if (selectedItem != null) {
            selectedItemName = selectedItem.getItemName(); // Get the item's name
        }
    }
    model.addAttribute("selectedItemName", selectedItemName);
    model.addAttribute("orders", orders);
        
       


        return "receiveditem";
    }

@PostMapping("/recordReceiveditem")
    public String saveReceivedItem(@ModelAttribute("receivedItem") ReceivedItem receivedItem,
     Model model, @RequestParam("itemID") Integer itemID,
     @RequestParam("orderID") Long orderID){
        // Find the item by ID
        {
            if (itemID == null || orderID == null) {
                model.addAttribute("error", "Please select an item and an order.");
                return "redirect:/receivedItem";
            }
        
            // Retrieve the item
            Item item = itemService.findItemById(itemID);
            if (item == null) {
                model.addAttribute("error", "Item not found.");
                return "redirect:/receivedItem";
            }
        
            // Retrieve the purchase order
            PurchaseOrder purchaseOrder = purchaseOrderService.findOrderByID(orderID);
            if (purchaseOrder == null) {
                model.addAttribute("error", "Purchase order not found.");
                return "redirect:/receivedItem";
            }
    
    receivedItem.setItem(item);
    receivedItem.setPurchaseOrder(purchaseOrder);

// Add received quantity to the existing quantity (starts at 0 by default)
Double newQuantity = item.getQuantity() + receivedItem.getReceiveedquantity();
item.setQuantity(newQuantity);

// Update the order status to "Complete"
purchaseOrder.setOrderStatus("Complete");

// Save updated item and received item
itemService.defineItem(item);
receivedItemService.additems(receivedItem);

model.addAttribute("message", "Item received and quantity updated.");
        return "redirect:/receivedItem";
    }

    
    }
}

