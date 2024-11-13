package com.example.asm_java5.Dao;


import com.example.asm_java5.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, String> {
    Category findByName(String name);
}
