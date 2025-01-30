package com.restaurant.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restaurant.inventory.model.Categories;
import com.restaurant.inventory.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    //creating the category
    public void defineCategory(Categories category){
        categoryRepository.save(category);
    }

    //retreiving the category list
    public List<Categories> getAllCategories(){
        return categoryRepository.findAll();
    }

    //finding the category using its id
   public Categories findById(int categoryID) {
        return categoryRepository.findById(categoryID)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryID));
    }

    //updating the category
    public void updateCategory(Categories category){
        categoryRepository.save(category);
    }
    
    //deleting the category
    public void deleteCategoryById(int categoryID){
        categoryRepository.deleteById(categoryID);
    }



}
