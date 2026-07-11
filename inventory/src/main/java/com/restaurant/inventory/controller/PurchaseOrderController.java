package com.restaurant.inventory.controller;



import java.util.Arrays;
import java.util.List;
//import java.util.Map;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
//import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.repository.PurchaseOrderRepository;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.PurchaseOrderService;
import com.restaurant.inventory.service.SupplierLinkageService;

import com.restaurant.inventory.service.UnitService;

@Controller
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    
    @Autowired
    private SupplierLinkageService supplierLinkageService;

    @Autowired
    private UnitService unitService;

   

    @Autowired
    private ItemService itemService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @GetMapping("/purchase/purchaseOrder")
    public String showPurchaseOrderForm(Model model,@RequestParam(required = false) Integer itemID){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        model.addAttribute("purchaseOrder", purchaseOrder);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);

        List<Supplier> suppliers = List.of();
        String selectedItemName = null;
        Unit selectedUnit = null;

        if (itemID != null) {
            suppliers = supplierLinkageService.getSuppliersByItemID(itemID);

            Item selectedItem = itemService.findItemById(itemID); // Fetch the selected item
        if (selectedItem != null) {
            selectedItemName = selectedItem.getItemName(); // Get the item's name
            selectedUnit = selectedItem.getUnit(); // Get the unit of the item
        }
        }

        model.addAttribute("selectedItemName", selectedItemName);
        
        model.addAttribute("selectedUnit", selectedUnit); // Pass the unit to the view
        
        model.addAttribute("suppliers", suppliers);

         



        return "purchaseorder";
    }

    @PostMapping("/saveOrder")
    public String saveOrder(
            @ModelAttribute("purchaseOrder") PurchaseOrder purchaseOrder,
            @RequestParam("itemID") Integer itemID,
            @RequestParam("unitID") Integer unitID,
            RedirectAttributes redirectAttributes
    ) {

        Item item = itemService.findItemById(itemID);
        // Check if an order for the same item already exists
        
        boolean existingOrder = purchaseOrderRepository.existsByItemAndOrderStatusIn(item, Arrays.asList("generated","pending"));
        
        if (existingOrder) {
            redirectAttributes.addFlashAttribute("errorMessage", "An order for this item already exists.");
            return "redirect:/purchase/purchaseOrder";
        }
    
        // Fetch unit and item
        Unit unit = unitService.findById(unitID);
        purchaseOrder.setUnit(unit);
        
       
        purchaseOrder.setItem(item);
    
        // Fetch supplier from supplier linkage service
        List<Supplier> linkedSuppliers = supplierLinkageService.getSuppliersByItemID(itemID);
        Supplier supplier = linkedSuppliers.isEmpty() ? null : linkedSuppliers.get(0); 
    
        if (supplier != null) {
            purchaseOrder.setSupplier(supplier);
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "No supplier linked for the selected item.");
            return "redirect:/purchase/purchaseOrder";
        }
    
        // Set order status
        purchaseOrder.setOrderStatus("pending");
    
        // Save the order
        PurchaseOrder saveOrder =  purchaseOrderService.createOrder(purchaseOrder);

        redirectAttributes.addFlashAttribute("successMessage", "order created successfully!");
        redirectAttributes.addFlashAttribute("savedOrderID" , saveOrder.getOrderID());
       
    
        return "redirect:/purchase/purchaseOrder";
    }

    //method to get the order details for printing
    @GetMapping("/purchase/getOrderDetails/{orderID}")
    public ResponseEntity<String> getOrderDetails(@PathVariable Long orderID) throws JsonProcessingException{
        PurchaseOrder order = purchaseOrderService.findOrderByID(orderID);

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Map.of(
            "orderID", order.getOrderID(),
            "orderDate", order.getOrderDate().toString(),
            "itemName", order.getItem().getItemName(),
            "supplierName", order.getSupplier().getSupplierName(),
            "supplierPhone", order.getSupplier().getPhoneNumber1(),
            "quantity", order.getQuantity(),
            "unit", order.getUnit().getUnitAbbriviation()

        ));

        return ResponseEntity.ok(json);


    }
    

    @GetMapping("/purchase/orderUpdate/{orderID}")
    public String showOrderEditForm(@PathVariable("orderID")long orderID, Model model,@RequestParam(required = false)Integer itemID, RedirectAttributes redirectAttributes){
        PurchaseOrder purchaseOrder = purchaseOrderService.findOrderByID(orderID);

         // Check order status
    String status = purchaseOrder.getOrderStatus();
    if ("Complete".equalsIgnoreCase(status)) {
        redirectAttributes.addFlashAttribute("errorMessage", "Order cannot be edited since it is completed.");
        return "redirect:/purchase";
    } else if ("CANCELLED".equalsIgnoreCase(status)) {
        redirectAttributes.addFlashAttribute("errorMessage", "Order cannot be edited since it is canceled.");
        return "redirect:/purchase";
    }

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
                                RedirectAttributes redirectAttributes,
                                @RequestParam (value ="cancel", required = false  ) boolean cancel,
                                @RequestParam(value = "cancelationReason", required = false) String cancelationReason,
                                @RequestParam("itemID")Integer itemID,
                                @RequestParam("unitID") Integer unitID
                                ){
                                    Unit unit = unitService.findById(unitID);
                                    purchaseOrder.setUnit(unit);    

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

            redirectAttributes.addFlashAttribute("successMessage", "Order updated successfully!");
            redirectAttributes.addFlashAttribute("savedOrderID", purchaseOrder.getOrderID());
        return "redirect:/purchase/orderUpdate/" + purchaseOrder.getOrderID();
    }

}
