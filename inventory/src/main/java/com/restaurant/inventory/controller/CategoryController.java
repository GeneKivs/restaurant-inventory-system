package com.restaurant.inventory.controller;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.restaurant.inventory.model.Categories;
import com.restaurant.inventory.service.CategoryService;

@Controller
public class CategoryController {
     
    //@Autowired
    //private CategoryService categoryService;
    private final CategoryService categoryService;
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }


    @GetMapping("/inventory/category")
    public String showCategoryform(Model model){
        Categories category = new Categories();
        model.addAttribute("category", category);

        List<Categories> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categoryform";
    }

    @PostMapping("/saveCategory")
    public String saveCategory(@ModelAttribute("category") Categories category){
        System.out.println(category);
        categoryService.defineCategory(category);
        return "redirect:/inventory/category";
    }

    @GetMapping("/inventory/categoryupdate/{categoryID}")
    public String showCategoryeditform(@PathVariable("categoryID")int categoryID, Model model){
        Categories category = categoryService.findById(categoryID);
        model.addAttribute("category", category);
        return "categoryeditform";
    }
    
    @PostMapping("/updateCategory")
    public String updateCategory(@ModelAttribute("category")Categories category){
        categoryService.updateCategory(category);
        return "redirect:/inventory/category";
    }

    @PostMapping("/deleteCategory/{categoryID}")
    public String deleteCategory(@PathVariable("categoryID")int categoryID){
        categoryService.deleteCategoryById(categoryID);
        return "redirect:/category";
    }
}
