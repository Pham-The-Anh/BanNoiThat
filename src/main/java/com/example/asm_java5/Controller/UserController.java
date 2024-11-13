package com.example.asm_java5.Controller;

import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
@Controller
public class UserController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/user")
    public String user(Model model) {
        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable String id, Model model) {
        Account account = accountService.findById(id).orElse(null);
        if (account == null) {
            // Nếu không tìm thấy tài khoản, trả về trang 404 hoặc thông báo lỗi
            return "redirect:/error"; // Hoặc bạn có thể hiển thị thông báo lỗi trong model
        }
        model.addAttribute("account", account);
        return "editUser"; // Tên trang hiển thị form sửa tài khoản
    }

    // Xử lý cập nhật tài khoản
    @PostMapping("/users/update") // Đổi đường dẫn để khớp với form
    public String updateUser(@ModelAttribute Account account) {
        // Kiểm tra xem account có null không trước khi gọi service
        if (account == null || account.getEmail() == null) {
            return "redirect:/users"; // Hoặc hiển thị thông báo lỗi
        }
        accountService.update(account);
        return "redirect:/users"; // Chuyển hướng về danh sách tài khoản
    }

    // Xử lý xóa tài khoản
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id) {
        accountService.deleteById(id);
        return "redirect:/users"; // Chuyển hướng về danh sách tài khoản
    }
}
