package com.example.asm_java5.Dao;

import com.example.asm_java5.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductDao extends JpaRepository<Product, String> {
    Optional<Product> findById(String productID);
    Page<Product> findByCategory_Name(String categoryName, Pageable pageable);
    Page<Product> findAllByproductNameLike(String productName, Pageable pageable);
    Page<Product> findAllByProductNameLikeAndCategory(String keywords, String category, Pageable pageable);

}
