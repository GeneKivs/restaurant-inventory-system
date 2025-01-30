package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.PurchaseOrderService;
import com.restaurant.inventory.service.SupplierLinkageService;

import com.restaurant.inventory.service.UnitService;

@Controller
public class PurchaseOrderController {
    
    @Autowired
    private SupplierLinkageService supplierLinkageService;

    @Autowired
    private UnitService unitService;

   

    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/purchaseOrder")
    public String showPurchaseOrderForm(Model model,@RequestParam(required = false) Integer itemID){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        model.addAttribute("purchaseOrder", purchaseOrder);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);

        List<Supplier> suppliers = List.of();
        String selectedItemName = null;

        if (itemID != null) {
            suppliers = supplierLinkageService.getSuppliersByItemID(itemID);

            Item selectedItem = itemService.findItemById(itemID); // Fetch the selected item
        if (selectedItem != null) {
            selectedItemName = selectedItem.getItemName(); // Get the item's name
        }
        }

        model.addAttribute("selectedItemName", selectedItemName);
        
             
        
        model.addAttribute("suppliers", suppliers);

         



        return "purchaseorder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(@ModelAttribute("purchaseOrder")PurchaseOrder purchaseOrder, @RequestParam("itemID")Integer itemID){

        Item item = itemService.findItemById(itemID);
        purchaseOrder.setItem(item);


        purchaseOrder.setOrderStatus("pending");
        purchaseOrderService.createOrder(purchaseOrder);
        return "redirect:/purchaseOrder";
    }

    @GetMapping("/orderUpdate/{orderID}")
    public String showOrderEditForm(@PathVariable("orderID")long orderID, Model model,@RequestParam(required = false)Integer itemID){
        PurchaseOrder purchaseOrder = purchaseOrderService.findOrderByID(orderID);
        model.addAttribute("purchaseOrder", purchaseOrder);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);

        

        
        if (itemID == null && purchaseOrder.getItem() != null) {
            itemID = purchaseOrder.getItem().getItemID();
        }
    
        List<Supplier> suppliers = itemID != null 
                ? supplierLinkageService.getSuppliersByItemID(itemID)
                : List.of();
        model.addAttribute("suppliers", suppliers);

        
        
        return "orderedit";
    }

    @PostMapping("/updateOrder")
    public String updateOrder(@ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrder,
                                @RequestParam (value ="cancel", required = false  ) boolean cancel,
                                @RequestParam(value = "cancelationReason", required = false) String cancelationReason,
                                @RequestParam("itemID")Integer itemID
                                ){
                                    Item item = itemService.findItemById(itemID);
                                    if (item != null) {
                                        purchaseOrder.setItem(item);
                                    } else {
                                        // Handle the case where the item is null (optional)
                                        throw new IllegalArgumentException("Item not found for itemID: " + itemID);
                                    }
                                    
                                    
            if (cancel) {
                purchaseOrder.setOrderStatus("CANCELLED");
                purchaseOrder.setCancelationReason(cancelationReason);
            }
            else if ("CANCELLED".equalsIgnoreCase(purchaseOrder.getOrderStatus())) {
                // If unchecking cancel, reset status and reason
                purchaseOrder.setOrderStatus("PENDING");
                purchaseOrder.setCancelationReason(null);
            }

            purchaseOrderService.updateOrder(purchaseOrder);
        return "redirect:/purchase";
    }
}
