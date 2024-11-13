package com.example.asm_java5.Dao;

import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Cart;
import com.example.asm_java5.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {
    List<Cart> findByAccount(Account account); // Tìm giỏ hàng của một tài khoản

    Optional<Cart> findByAccountAndProduct(Account account, Product product); // Tìm giỏ hàng của một tài khoản theo sản phẩm

    void deleteByAccountAndProduct(Account account, Product product); // Xóa sản phẩm khỏi giỏ hàng của tài khoản

    void deleteByAccountEmail(String email); // Xóa giỏ hàng của một tài khoản theo email
}
