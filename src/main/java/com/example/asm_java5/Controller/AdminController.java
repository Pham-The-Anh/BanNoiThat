package com.example.asm_java5.Controller;

import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Controller
public class AdminController {
   @Autowired
   private AccountService accountService;

    @GetMapping("/login/admin")
    public String loginadmin() {
        return "loginadmin";
    }

    // Xử lý đăng nhập cho admin
    @PostMapping("/login/admin")
    public String loginadmin(@RequestParam("email") String email,
                             @RequestParam("password") String password, Model model) {
        // Gọi service để kiểm tra tài khoản
        Account account = accountService.login(email, password);
        if (account != null) {
            // Kiểm tra xem tài khoản có phải là admin không
            if (account.isAdmin()) {
                // Nếu là admin, chuyển hướng đến trang admin dashboard
                return "redirect:/admin";
            } else {
                // Nếu không phải admin, hiển thị thông báo lỗi
                model.addAttribute("error", "Bạn không có quyền truy cập với vai trò admin.");
                return "loginadmin";
            }
        } else {
            // Nếu thông tin đăng nhập không hợp lệ, hiển thị lỗi
            model.addAttribute("error", "Email hoặc mật khẩu không đúng.");
            return "loginadmin";
        }
    }

    // Trang dashboard dành cho admin
//    @GetMapping("/admin/dashboard")
//    public String dashboard(Model model) {
//        List<Account> accounts = accountService.findAll(); // Lấy danh sách tài khoản từ service
//        model.addAttribute("accounts", accounts); // Thêm danh sách tài khoản vào mô hình
//        return "admindemo";  // Tên của file HTML trang admin dashboard
//    }
}
