package com.restaurant.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.inventory.model.Categories;
import com.restaurant.inventory.model.Item;
import com.restaurant.inventory.model.Unit;
import com.restaurant.inventory.service.CategoryService;
import com.restaurant.inventory.service.ItemService;
import com.restaurant.inventory.service.UnitService;

@Controller
public class ItemController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UnitService unitService;
    
    @Autowired
    private ItemService itemService;

    @GetMapping("/item")
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
    public String saveItem(@ModelAttribute("item") Item item){
        System.out.println(item);
        itemService.defineItem(item);
        return "redirect:/item";
    }

    @GetMapping("/itemUpdate/{itemID}")
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
    public String deleteItem(@PathVariable("itemID")int itemID){
        itemService.deleteItemById(itemID);
        return "redirect:/item";
    }

}
