package com.example.asm_java5.Service;

import com.example.asm_java5.Dao.ProductDao;
import com.example.asm_java5.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public Page<Product> getProducts(String categoryName, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return productDao.findByCategory_Name(categoryName, pageable); // Tìm theo tên danh mục
    }
    public List<Product> getProducts() {
        return productDao.findAll();
    }
}
