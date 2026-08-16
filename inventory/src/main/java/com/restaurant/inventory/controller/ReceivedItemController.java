package com.restaurant.inventory.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    /*@Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private UnitService unitService;

    @Autowired
    private ReceivedItemService receivedItemService;*/

    private final ItemService itemService;
    private final PurchaseOrderService purchaseOrderService;
    private final UnitService unitService;
    private final ReceivedItemService receivedItemService;

    public ReceivedItemController(ItemService itemService, PurchaseOrderService purchaseOrderService, ReceivedItemService receivedItemService, UnitService unitService){
        this.itemService = itemService;
        this.purchaseOrderService = purchaseOrderService;
        this.unitService = unitService;
        this.receivedItemService = receivedItemService;

    }



    @GetMapping("/inventory/receivedItem")
    public String showReceivedItemForm(Model model,@RequestParam( value = "itemID" , required = false)  Integer itemID){
        ReceivedItem receivedItem = new ReceivedItem();
        model.addAttribute("receivedItem", receivedItem);

       model.addAttribute("filterOrders", new ArrayList<>()); 
         List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

       

        List<PurchaseOrder> orders = new ArrayList<>();
        String selectedItemName = null;
        Unit selectedUnit = null;

    if (itemID != null) {
        orders = purchaseOrderService.getPendingOrdersByItemID(itemID);
        
        Item selectedItem = itemService.findItemById(itemID); // Fetch the selected item
        if (selectedItem != null) {
            selectedItemName = selectedItem.getItemName(); // Get the item's name
            selectedUnit = selectedItem.getUnit();
        }
            
        
    }
    model.addAttribute("selectedItemName", selectedItemName);
    model.addAttribute("selectedUnit", selectedUnit);
    model.addAttribute("orders", orders);
        
       


        return "receiveditem";
    }

@PostMapping("/recordReceiveditem")
    public String saveReceivedItem(@ModelAttribute("receivedItem") ReceivedItem receivedItem,
     Model model, @RequestParam("itemID") Integer itemID,
     @RequestParam("orderID") Long orderID,
     @RequestParam("unitID") Integer unitID,
     @RequestParam(value = "damagequantity", required = false, defaultValue = "0") double damagedquantity,
     RedirectAttributes redirectAttributes){

        LocalDate today = LocalDate.now();
        LocalDate expiryDate = receivedItem.getExpiryDate();
        if( expiryDate !=null && !expiryDate.isAfter(today)){
            redirectAttributes.addFlashAttribute("errorMessage", "Expiry date must be a future date.");
    return "redirect:/inventory/receivedItem";
        }

        Unit unit = unitService.findById(unitID);
        receivedItem.setUnit(unit);
        // Find the item by ID
        {
            if (itemID == null || orderID == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "no purchase order for ths Item .");
                return "redirect:/inventory/receivedItem";
            }
        
            // Retrieve the item
            Item item = itemService.findItemById(itemID);
            if (item == null) {
                model.addAttribute("error", "Item not found.");
                return "redirect:/inventory/receivedItem";
            }
        
            // Retrieve the purchase order
            PurchaseOrder purchaseOrder = purchaseOrderService.findOrderByID(orderID);
            if (purchaseOrder == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "There is no purchase order on this Item . ");
                return "redirect:/inventory/receivedItem";
            }

            double receivedquantity = receivedItem.getReceiveedquantity();
            
            
            double ordequantity = purchaseOrder.getQuantity();

            
                double storingquantity = receivedquantity - damagedquantity;   
            
            
            
            double newpendingquantity = ordequantity - storingquantity;

            if (receivedquantity >= ordequantity && newpendingquantity == 0){
                purchaseOrder.setOrderStatus("Complete");
                receivedItem.setReceivedStatus("Complete");
            }else{
                purchaseOrder.setOrderStatus("Incomplete");
                receivedItem.setReceivedStatus("Incomplete");
            }

             
            

    
    receivedItem.setItem(item);
    receivedItem.setPendingquantity(newpendingquantity);
    receivedItem.setDamagequantity(damagedquantity);
    receivedItem.setPurchaseOrder(purchaseOrder);

// Add received quantity to the existing quantity (starts at 0 by default)
Double newQuantity = item.getQuantity() + storingquantity;
item.setQuantity(newQuantity);



// Save updated item and received item
itemService.defineItem(item);
receivedItemService.additems(receivedItem);

redirectAttributes.addFlashAttribute("successMessage", "Item received  successfully and the stock is updated.");

        return "redirect:/inventory/receivedItem";
    }

    
    }

    @GetMapping("/inventory/receivedlist")
    public String showReceivedList(Model model){
        List<ReceivedItem> receivedItems = receivedItemService.getALLReceivedItems();
        model.addAttribute("receivedItems", receivedItems);
        return "receivedlist";
    }

    @GetMapping("/inventory/receivededit/{receivedID}")
    public String showeditform(@PathVariable("receivedID")Long receivedID, Model model){
        ReceivedItem receivedItem = receivedItemService.finnReceivedItembyID(receivedID);
        model.addAttribute("receivedItem", receivedItem);
        return "receivededit";
    }

    @PostMapping("/updateReceivedItem")
    public String updateItem(@ModelAttribute("receivedItem") ReceivedItem receivedItem,
    @RequestParam("itemID")Integer itemID,
    @RequestParam("orderID") Long orderID,
    @RequestParam("unitID") Integer unitID,
    RedirectAttributes redirectAttributes){
       // Retrieve the existing received item record
    ReceivedItem existingItem = receivedItemService.finnReceivedItembyID(receivedItem.getReceivedID());

    if (existingItem == null) {
        return "redirect:/inventory/receivedlist?error=ItemNotFound";
    }

    // Keep the original received date
    receivedItem.setReceivedDate(existingItem.getReceivedDate());

    // Preserve the existing damaged quantity
    receivedItem.setDamagequantity(existingItem.getDamagequantity());

    // Retrieve related entities
    Item item = itemService.findItemById(itemID);
    receivedItem.setItem(item);

    PurchaseOrder purchaseOrder = purchaseOrderService.findOrderByID(orderID);
    receivedItem.setPurchaseOrder(purchaseOrder);

    Unit unit = unitService.findById(unitID);
    receivedItem.setUnit(unit);

    double receivingnewQuantity = receivedItem.getReceiveedquantity();
    double newReceivedQuantity = existingItem.getReceiveedquantity() + receivingnewQuantity;
    double newPendingQuantity = existingItem.getPendingquantity() - receivingnewQuantity;

    
    if( receivingnewQuantity == existingItem.getPendingquantity()){// Calculate the updated received quantity
        
        receivedItem.setReceiveedquantity(newReceivedQuantity);
        // Calculate new pending quantity
    
    receivedItem.setPendingquantity(newPendingQuantity);
    

    

    // Update order and received status if pending quantity is 0
    if (newPendingQuantity == 0) {
        purchaseOrder.setOrderStatus("Complete");
        receivedItem.setReceivedStatus("Complete");
    } else {
        purchaseOrder.setOrderStatus("Incomplete");
        receivedItem.setReceivedStatus("Incomplete");
    }


    // Update item stock (adding the newly received quantity to existing stock)
    double updatedStock = item.getQuantity() + receivingnewQuantity;
    item.setQuantity(updatedStock);
    itemService.defineItem(item);

    // Save updated received item
    receivedItemService.updateReceivedItem(receivedItem);

    redirectAttributes.addFlashAttribute("successMessage", "Item received updated successfully and the stock is updated.");
    }else {
        // Redirect with error message if received quantity doesn't match pending quantity
        redirectAttributes.addFlashAttribute("errorMessage", "Error: The quantity received does not match the pending quantity or there is no pending quantity for this item.");
        return "redirect:/inventory/receivedlist";
    }
        return "redirect:/inventory/receivedlist";

    }
}

