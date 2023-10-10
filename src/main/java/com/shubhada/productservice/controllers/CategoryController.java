package com.shubhada.productservice.controllers;

import com.shubhada.productservice.dtos.CategoryDTO;
import com.shubhada.productservice.models.Category;
import com.shubhada.productservice.models.Product;
import com.shubhada.productservice.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products/categories")

public class CategoryController {
    private  CategoryService categoryService;
    public CategoryController(CategoryService categoryService){

        this.categoryService=categoryService;
    }
    @GetMapping("")
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();

        //return "Getting All Categories";
    }
    @GetMapping("/{category}")
    public List<Product> getProductsInCategory(@PathVariable("category") String category){
        return categoryService.getProductsInCategory(category);
        //return "Getting Products In Category is: "+category;
    }
}
