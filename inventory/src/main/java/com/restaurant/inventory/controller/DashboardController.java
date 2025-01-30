package com.restaurant.inventory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.inventory.repository.ItemRepository;
import com.restaurant.inventory.repository.SupplierRepository;
import com.restaurant.inventory.service.PurchaseOrderService;

@Controller
public class DashboardController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @GetMapping("/")
    public String showDashboard(Model model){

        long itemCount = itemRepository.count();
        long supplierCount = supplierRepository.count();
        long pendingOrdersCount = purchaseOrderService.countPendingOrders();
        model.addAttribute("pendingOrdersCount", pendingOrdersCount);

        model.addAttribute("itemCount", itemCount);
        model.addAttribute("supplierCount", supplierCount);

        return "dashboard";
    }

}
