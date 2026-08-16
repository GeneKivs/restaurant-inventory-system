package com.restaurant.inventory.controller;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.service.ItemService;

@Controller
public class InventoryModuleController {

    //@Autowired
    //private ItemService itemService;

    private final ItemService itemService;
    public InventoryModuleController(ItemService itemService){
        this.itemService = itemService;
    }

    @GetMapping("/inventory")
    public String showInventoryModuleform(Model model){
        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);
        return "inventorymodule";
    }

}
