package com.example.asm_java5.Controller;


import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Product;
import com.example.asm_java5.Service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"account", "fullname"}) // Lưu account và fullname vào session
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("/dangnhap")
    public String dangnhap() {
        return "dangnhap";
    }

    @PostMapping("/dangnhap")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model,
                        RedirectAttributes redirectAttributes, HttpServletResponse httpServletResponse) {
        Account account = accountService.login(email, password);

        if (account != null) {
            session.setAttribute("account", account);
            session.setAttribute("fullname", account.getFullname());

            Cookie userCookie = new Cookie("user", email);
            userCookie.setMaxAge(60 * 60);  // Thời gian sống của cookie
            userCookie.setPath("/");  // Đảm bảo cookie có thể truy cập trên toàn bộ website
            httpServletResponse.addCookie(userCookie);

            System.out.println("Đăng nhập thành công! Tên: " + account.getFullname());
            return "redirect:/index";
        } else {
            if (accountService.emailExists(email)) {
                redirectAttributes.addFlashAttribute("error", "Mật khẩu không đúng");
            } else {
                redirectAttributes.addFlashAttribute("error", "Email không tồn tại");
            }
            return "redirect:/dangnhap";
        }
    }


    @RequestMapping("/logout")
    public String logout(Model model,HttpSession session) {
        // Xóa tài khoản khỏi session
        session.invalidate(); // Xóa toàn bộ session
        model.asMap().remove("account"); // Xóa tài khoản khỏi session
        model.asMap().remove("fullname"); // Xóa fullname khỏi session
        return "redirect:/index"; // Quay lại trang đăng nhập
    }

    @GetMapping("/dangky")
    public String dangky(Model model) {
        model.addAttribute("account", new Account());
        return "dangky";
    }

    @PostMapping("/dangky")
    public String register(@ModelAttribute Account account,
                           @RequestParam(value = "admin", required = false) Boolean admin,
                           RedirectAttributes redirectAttributes) {
// Kiểm tra xem email có tồn tại không trước khi đăng ký
        if (accountService.emailExists(account.getEmail())) {
            redirectAttributes.addFlashAttribute("error", "Email đã tồn tại"); // Thông báo lỗi
            redirectAttributes.addFlashAttribute("account", account); // Giữ lại thông tin đã nhập
            return "redirect:/dangky";
        }

        // Kiểm tra nếu checkbox không được chọn
        if (admin == null || !admin) {
            redirectAttributes.addFlashAttribute("error", "Bạn cần đồng ý với các điều khoản để tiếp tục!");
            redirectAttributes.addFlashAttribute("account", account); // Giữ lại thông tin đã nhập
            return "redirect:/dangky";
        }

        account.setAdmin(false); // Mặc định không phải là admin
        accountService.registerAccount(account); // Đăng ký tài khoản
        redirectAttributes.addFlashAttribute("message", "Đăng ký thành công!");
        return "redirect:/dangky";
    }
    @RequestMapping("/admin")
    public String viewadmin(Model model) {
        model.addAttribute("account", new Account());
        model.addAttribute("product", new Product());
        model.addAttribute("message", "");  // Thông báo
        return "admin";


    }
}
