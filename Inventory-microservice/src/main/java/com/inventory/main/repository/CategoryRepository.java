package com.inventory.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.main.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
