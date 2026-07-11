package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.model.Supplier;
import com.restaurant.inventory.service.PurchaseOrderService;
import com.restaurant.inventory.service.SupplierService;

@Controller
public class SupplierController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;
    
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/purchase/supplier")
    public String showSupplierForm(Model model){
        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);

        List <Supplier> suppliers =  supplierService.getALLsuppliers();
        model.addAttribute("suppliers", suppliers);
        return "supplierform";
    }

    @PostMapping("/saveSupplier")
    public String saveSupplier(@ModelAttribute("supplier") Supplier supplier){
        System.out.println(supplier);
        supplierService.defineSupplier(supplier);
        return "redirect:/purchase/supplier";
    }

    @GetMapping("/purchase/supplierEdit/{supplierID}")
    public String showeditformString(@PathVariable("supplierID")int supplierID, Model model){
        Supplier supplier = supplierService.findSupplierById(supplierID);
        model.addAttribute("supplier", supplier);
        return "supplieredit";
    }

    @PostMapping("updateSupplier")
    public String updateSupplier(@ModelAttribute("supplier") Supplier supplier){
        supplierService.updateSupplier(supplier);
        return "redirect:/purchase/supplier";
    }

    @PostMapping("/deleteSupplier/{supplierID}")
    public String deleteSupplier(@PathVariable("supplierID")int supplierID,RedirectAttributes redirectAttributes){
        Supplier supplier = supplierService.findSupplierById(supplierID);

        //check if the supplier is involved in transaction
        boolean isinTransaction = purchaseOrderService.isSupplierInTransaction(supplier);

        if(isinTransaction){
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete. Supplier is involved in a purchaseOrder transaction.");
            return "redirect:/purchase/supplier";
        }
        supplierService.deleteSupplierById(supplierID);
        redirectAttributes.addFlashAttribute("successMessage", "supplier deleted successfully.");
        return "redirect:/purchase/supplier";
    }

}
