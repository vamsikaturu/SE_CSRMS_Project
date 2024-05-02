package com.pack.seproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pack.seproject.model.Category;

public interface CategoryRepository  extends JpaRepository<Category, Integer>{

    Category findByCategoryName(String categoryName);

    List<Category> findByUserId(int i);
    
}
