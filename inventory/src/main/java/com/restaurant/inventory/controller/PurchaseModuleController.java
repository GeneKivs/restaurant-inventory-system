package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.inventory.model.PurchaseOrder;
import com.restaurant.inventory.service.PurchaseOrderService;

@Controller
public class PurchaseModuleController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;


    @GetMapping("/purchase")
    public String showPurchaseModuleForm(Model model){
        List <PurchaseOrder> orders = purchaseOrderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "purchasemodule";
    }

}
