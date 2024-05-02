package com.pack.seproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.pack.seproject.model.Category;
import com.pack.seproject.model.User;
import com.pack.seproject.service.CategoryService;
import com.pack.seproject.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/category")
    public String categoryPage(@RequestParam String username, Model m) {
        m.addAttribute("username", username);
        m.addAttribute("addcategory", new Category());
        return "add-category-page";
    }

    @PostMapping("/addcategory")
    public String addCategory(Category category, Model m) {
        User user = userService.findByUsername(category.getUser().getUsername());
        Category category2 = new Category(category.getCategoryName(), user);

        categoryService.saveCategory(category2);

        return "redirect:/userhome";
    }
    

}
