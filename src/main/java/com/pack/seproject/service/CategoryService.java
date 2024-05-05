package com.pack.seproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pack.seproject.model.Category;
import com.pack.seproject.repository.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
	CategoryRepository categoryRepository;

	public List<Category> getCategoryList(int id) {
		return categoryRepository.findByUserId(id);
	}

    public Category findByCategoryName(String categoryName) {
       return categoryRepository.findByCategoryName(categoryName);
    }

    public List<Category> findByUserId(int id) {
        return categoryRepository.findByUserId(id);
    }

    public Category saveCategory(Category category) {
       return categoryRepository.save(category);
    }
    
}
