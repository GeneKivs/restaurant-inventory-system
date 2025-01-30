package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.model.SupplierLinkage;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.SupplierLinkageService;
import com.restaurant.inventory.service.SupplierService;

@Controller
public class SupplierLinkageController {

    @Autowired
    private SupplierLinkageService supplierLinkageService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ItemService itemService;

    @GetMapping("/supplierItem")
    public String showLinkagefrom(Model model){
        SupplierLinkage supplierLinkage = new SupplierLinkage();
        model.addAttribute("supplierLinkage", supplierLinkage);

        List <SupplierLinkage> supplierLinkages = supplierLinkageService.getAllLinks();
        model.addAttribute("supplierLinkages", supplierLinkages);

         List <Supplier> suppliers =  supplierService.getALLsuppliers();
        model.addAttribute("suppliers", suppliers);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        return  "supplieritem";
    }

    @PostMapping("/saveLink")
    public String saveLink(@ModelAttribute("supplierLinkage") SupplierLinkage supplierLinkage){
        System.out.println(supplierLinkage);
        supplierLinkageService.defineLink(supplierLinkage);
        return "redirect:/supplierItem";
    }

    @GetMapping("/linkUpdate/{supplierLinkID}")
    public String showLinkUpdateForm(@PathVariable("supplierLinkID") int supplierLinkID, Model model){
        SupplierLinkage supplierLinkage = supplierLinkageService.findLinkById(supplierLinkID);
        model.addAttribute("supplierLinkage", supplierLinkage);

        List <Supplier> suppliers =  supplierService.getALLsuppliers();
        model.addAttribute("suppliers", suppliers);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);


        return "supplieritemedit";
    }

    @PostMapping("/updateLinkage")
    public String updatelinkage(@ModelAttribute("supplierLinkage") SupplierLinkage supplierLinkage){
        supplierLinkageService.updateLink(supplierLinkage);
        return "redirect:/supplierItem";
    }

    @PostMapping("/deleteLinkage/{supplierLinkID}")
    public String deleteLinkString(@PathVariable("supplierLinkID") int supplierLinkID){
        supplierLinkageService.deleteLinkById(supplierLinkID);
        return "redirect:/supplierItem";
    }

}
