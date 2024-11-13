package com.example.asm_java5.Service;

import com.example.asm_java5.Dao.AccountDao;
import com.example.asm_java5.Dao.CartDao;
import com.example.asm_java5.Dao.ProductDao;
import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Cart;
import com.example.asm_java5.Entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CartServicelmp {

    @Autowired
    private CartDao cartDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    AccountDao accountDao;
    public List<Cart> getCartItemsByUser(Account account) {
        return cartDao.findByAccount(account);
    }


    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(Account account, Product product, int quantity) {
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng chưa
        Optional<Cart> existingCart = cartDao.findByAccountAndProduct(account, product);
        if (existingCart.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ, tăng số lượng sản phẩm
            Cart cart = existingCart.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            cartDao.save(cart);
        } else {
            // Nếu sản phẩm chưa có trong giỏ, tạo mới
            Cart cart = new Cart();
            cart.setAccount(account);
            cart.setProduct(product);
            cart.setQuantity(quantity);
            cartDao.save(cart);
        }
    }

    // Tìm sản phẩm theo ID
    public Product findProductById(String productId) {
        return productDao.findById(productId).orElse(null);  // Tìm sản phẩm theo ID
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(Integer id) {
        cartDao.deleteById(id);  // Xóa sản phẩm khỏi giỏ hàng
    }

    // Xóa tất cả sản phẩm trong giỏ hàng của người dùng
    public void clearCart(Account account) {
        cartDao.deleteByAccountEmail(account.getEmail());  // Xóa giỏ hàng theo email tài khoản
    }

    public double getTotalPrice(List<Cart> cartItems) {
        double totalPrice = 0;
        for (Cart cart : cartItems) {
            // Assuming `product.getPrice()` returns the price of the product and cart.getQuantity() gives the quantity
            totalPrice += cart.getProduct().getPrice() * cart.getQuantity();
            cart.getProduct().setFormattedPrice(Product.formatCurrency(cart.getProduct().getPrice()));
        }
        String formattedTotalPrice  = Format.formatCurrency(totalPrice);
        return totalPrice;
    }// Đảm bảo phương thức này chạy trong một transaction
    @Transactional
    public void updateCart(Integer ID, Integer quantity) {
        Optional<Cart> cart =  cartDao.findById(ID);
        if (cart.isPresent()) {
            Cart cart1 = cart.get();
            cart1.setQuantity(quantity);
            cartDao.save(cart1);
        }
    }

}
