package com.restaurant.inventory.controller;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.inventory.model.Notification;
import com.restaurant.inventory.repository.ItemRepository;
import com.restaurant.inventory.repository.SupplierRepository;
import com.restaurant.inventory.service.NotificationService;
import com.restaurant.inventory.service.PurchaseOrderService;

@Controller
public class DashboardController {

    /*@Autowired
    private NotificationService notificationService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private SupplierRepository supplierRepository;*/

    private final NotificationService notificationService;
    private final PurchaseOrderService purchaseOrderService;
    private final ItemRepository itemRepository;
    private final SupplierRepository supplierRepository;

    public DashboardController(ItemRepository itemRepository, NotificationService notificationService, PurchaseOrderService purchaseOrderService, SupplierRepository supplierRepository){
        this.notificationService = notificationService;
        this.purchaseOrderService = purchaseOrderService;
        this.itemRepository = itemRepository;
        this.supplierRepository = supplierRepository;

    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model){

        long itemCount = itemRepository.count();
        long supplierCount = supplierRepository.count();
        long pendingOrdersCount = purchaseOrderService.countPendingOrders();
        long generatedOrdersCount = purchaseOrderService.countGeneratedOrders();
        model.addAttribute("generatedOrdersCount", generatedOrdersCount);
        model.addAttribute("pendingOrdersCount", pendingOrdersCount);

        model.addAttribute("itemCount", itemCount);
        model.addAttribute("supplierCount", supplierCount);

        List<Notification> notifications = notificationService.getUnreadNotifications();
        model.addAttribute("notifications", notifications);

        return "dashboard";
    }

}
