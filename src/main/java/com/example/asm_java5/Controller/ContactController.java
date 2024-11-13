package com.example.asm_java5.Controller;

import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Contact;
import com.example.asm_java5.SendMail.Entity.MailInfo;
import com.example.asm_java5.Service.AccountService;
import com.example.asm_java5.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.util.List;

@Controller
public class ContactController {
    @Autowired
    ContactService contactService;
    @Autowired
    AccountService accountService;

    @RequestMapping("/contact")
    public String contact(Model model) {
    model.addAttribute("contact",new Contact());
    return "contact";
    }


    // Xử lý việc gửi thông tin liên hệ
    @PostMapping("/contact/submit")
    public String addContact(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes) {
        if (contact.getAccount() == null || contact.getAccount().getEmail() == null) {
            redirectAttributes.addFlashAttribute("mess", "Thông tin tài khoản không hợp lệ.");
            return "redirect:/contact";  // Chuyển hướng với thông báo lỗi
        }

        Account account = accountService.findAccountByEmail(contact.getAccount().getEmail());

        if (account == null) {
            redirectAttributes.addFlashAttribute("mess", "Bạn chưa đăng ký tài khoản. Vui lòng đăng ký trước.");
            return "redirect:/contact";  // Chuyển hướng với thông báo lỗi
        }

        contact.setAccount(account);  // Gán tài khoản vào liên hệ
        contact.setStatus(false);  // Mặc định trạng thái là chưa kiểm tra

        contactService.saveContact(contact);  // Lưu thông tin liên hệ

        redirectAttributes.addFlashAttribute("mess", "Đã gửi thành công!");  // Thông báo gửi thành công
        return "redirect:/contact";  // Chuyển hướng về trang liên hệ
    }



    // Admin: Hiển thị tất cả các liên hệ chưa được kiểm tra
    @RequestMapping("/admin/contact")
    public String adminViewContact(Model model) {
        List<Contact> contacts = contactService.getAllContacts();
        model.addAttribute("contacts", contacts);
        model.addAttribute("mailInfo", new MailInfo());
        return "contact-list";
    }


    @PostMapping("/admin/update-status/{id}")
    public String updateStatus(@PathVariable("id") Integer id) {
        contactService.updateStatus(id, true);              // Đánh dấu là đã kiểm tra
        return "redirect:/admin/contact";                           // Quay lại trang quản lý liên hệ
    }
}
