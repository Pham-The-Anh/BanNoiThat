package com.example.asm_java5.Controller;

import com.example.asm_java5.Dao.AccountDao;
import com.example.asm_java5.Dao.CartDao;
import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Cart;
import com.example.asm_java5.Entity.Product;
import com.example.asm_java5.Service.CartServicelmp;
import com.example.asm_java5.Service.Format;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("fullname")
public class CartController {

    @Autowired
    private CartServicelmp cartService;
    @Autowired
    AccountDao  accountDao;
    @Autowired
    CartDao cartDao;
    // Hiển thị giỏ hàng
    @RequestMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        String fullname = (String) session.getAttribute("fullname");
        if (fullname != null) {
            model.addAttribute("fullname", fullname);
        } else {
            model.addAttribute("fullname", "Guest");
        }

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/dangnhap";  // Redirect to login if account is null
        }

        // Kiểm tra cartItems có null hay không
        List<Cart> cartItems = cartService.getCartItemsByUser(account);
        if (cartItems == null || cartItems.isEmpty()) {
            model.addAttribute("emptyCart", true);  // Nếu giỏ hàng trống, thêm thuộc tính này vào model
        } else {
            model.addAttribute("cartItems", cartItems);
            double totalPrice = cartService.getTotalPrice(cartItems); // Lấy tổng giá trị thô
            String formattedTotalPrice = Format.formatCurrency(totalPrice);
            model.addAttribute("totalPrice", formattedTotalPrice);
        }

        return "cart";  // Trả về trang giỏ hàng
    }


    // Thêm sản phẩm vào giỏ hàng
    @RequestMapping("/cart/add")
    public String addToCart(RedirectAttributes redirectAttributes, HttpSession session,
                            @RequestParam("productId") String productId,
                            @RequestParam("quantity") Integer quantity) {

        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng!");
            return "redirect:/dangnhap";  // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
        }

        // Tìm sản phẩm theo productId
        Product product = cartService.findProductById(productId);
        if (product != null) {
            cartService.addToCart(account, product, quantity);  // Thêm sản phẩm vào giỏ hàng
            redirectAttributes.addFlashAttribute("success", "Đã thêm sản phẩm vào giỏ hàng!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại!");
        }

        return "redirect:/shop";  // Quay lại trang cửa hàng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @RequestMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable("id") Integer id, HttpSession session) {
        Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/dangnhap";  // Redirect to login if account is not found in session
        }

        // Sử dụng account từ session để xử lý
        cartService.removeFromCart(id);
        return "redirect:/cart";  // Điều hướng về giỏ hàng sau khi xóa
    }
    @PostMapping("/cart/update")
    public String updateCart(@RequestParam("cartItemId") Integer cartItemId,
                             @RequestParam("quantity") int quantity) {
        cartService.updateCart(cartItemId, quantity);
        return "redirect:/cart";
    }


//    @RequestMapping("/cart/update/{id}")
//    public String updateCartQuantity(@RequestParam("cartId") Integer cartId,
//                                     @RequestParam("quantity") int quantity, Model model , Account account) {
//        cartService.update(quantity, cartId);  // Gọi service để cập nhật giỏ hàng
//
//        // Tính lại tổng tiền sau khi cập nhật
//        List<Cart> cartItems = cartService.getCartItemsByUser(account); // Lấy danh sách giỏ hàng từ service
//        double total = 0.0;
//        for (Cart cart : cartItems) {
//            total += cart.getQuantity() * cart.getProduct().getPrice();  // Tính tổng cho từng sản phẩm
//        }
//
//        // Truyền tổng tiền vào model để hiển thị trên trang giỏ hàng
//        model.addAttribute("totalPrice", total);
//        model.addAttribute("cartItems", cartItems);  // Truyền giỏ hàng vào model
//
//        return "redirect:/cart";  // Redirect lại trang giỏ hàng
//    }

}
