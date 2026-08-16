package com.restaurant.inventory.controller;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.restaurant.inventory.model.Categories;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.CategoryService;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.PurchaseOrderService;
import com.restaurant.inventory.service.UnitService;

@Controller
public class ItemController {

    /*@Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;
    
    @Autowired
    private ItemService itemService;*/

    private final PurchaseOrderService purchaseOrderService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final ItemService itemService;

    public ItemController(CategoryService categoryService, ItemService itemService, PurchaseOrderService purchaseOrderService, UnitService unitService){
        this.purchaseOrderService = purchaseOrderService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.itemService = itemService;

    }

    //method to show the item definition form 
    @GetMapping("/inventory/item")
    public String showitemform(Model model){
        Item item =new Item();
        model.addAttribute("item", item);

        List <Item> items = itemService.getAllItems();
        model.addAttribute("items", items);

        List<Categories> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);


        return "itemform";
    }

    @PostMapping("/saveItem")
    public String saveItem(@ModelAttribute("item") Item item,RedirectAttributes redirectAttributes){
        System.out.println(item);
        itemService.defineItem(item);
        redirectAttributes.addFlashAttribute("successMessage", "Item created successfully.you can link it to supplier in the supplier linkage form");
        return "redirect:/inventory/item";
    }

    @GetMapping("/inventory/itemUpdate/{itemID}")
    public String showItemUpdateForm(@PathVariable("itemID")int itemID, Model model){
        Item item = itemService.findItemById(itemID);
        model.addAttribute("item", item);

        List<Categories> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        List<Unit> units = unitService.getAllUnits();
        model.addAttribute("units", units);


        return "itemupdate";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute("item") Item item){
        itemService.updateItem(item);
        return "redirect:/inventory";
    }

    @PostMapping("/deleteItem/{itemID}")
    public String deleteItem(@PathVariable("itemID")int itemID, RedirectAttributes redirectAttributes){
        Item item = itemService.findItemById(itemID);

        // Check if item still has stock
    if (item.getQuantity() > 0) {
        redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete. Item is still in stock.");
        return "redirect:/inventory";
    }
    // Check if item is involved in a transaction
    boolean isInTransaction = purchaseOrderService.isItemInTransaction(item);
    if (isInTransaction) {
        redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete. Item is involved in a purchaseOrder transaction.");
        return "redirect:/inventory";
    }
        itemService.deleteItemById(itemID);
        redirectAttributes.addFlashAttribute("successMessage", "Item deleted successfully.");
        return "redirect:/inventory";
    }

}
