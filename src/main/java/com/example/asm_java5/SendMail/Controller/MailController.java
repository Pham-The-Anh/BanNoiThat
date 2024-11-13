package com.example.asm_java5.SendMail.Controller;


import com.example.asm_java5.SendMail.Entity.MailInfo;
import com.example.asm_java5.SendMail.Service.MailerServiceImpl;
import jakarta.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MailController {
    @Autowired
    MailerServiceImpl mailerService;

    @Autowired
    ServletContext servletContext;

    @RequestMapping("email")
    public String sendMail(Model model , @RequestParam(value = "toEmail") String toEmail) {
        MailInfo mailInfo = new MailInfo();
        mailInfo.setToEmail(toEmail);
        model.addAttribute("mailInfo", mailInfo );
        model.addAttribute("message", ""); // Khởi tạo thông báo
        return "email";
    }

    @PostMapping("email/send-email")
    public String sendEmail(@Validated MailInfo mailInfo,

                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("message", "Có lỗi xảy ra. Vui lòng kiểm tra lại.");
            return "email";
        }

        // Tạo danh sách để lưu tên tệp đính kèm
        List<String> fileNames = new ArrayList<>();

        // Xử lý các tệp đính kèm từ MultipartFile
//        for (MultipartFile file : attachments) {
//            try {
//                if (!file.isEmpty()) {
//                    // Lưu tệp vào thư mục tạm (uploads)
//                    String fileName = file.getOriginalFilename();
//                    String filePath = servletContext.getRealPath("/uploads/") + fileName;
//                    File dest = new File(filePath);
//
//                    // Lưu tệp vào thư mục
//                    file.transferTo(dest);
//
//                    // Thêm tên tệp vào danh sách
//                    fileNames.add(fileName);
//                }
//            } catch (Exception e) {
//                model.addAttribute("message", "Lỗi khi tải tệp: " + e.getMessage());
//                return "email";
//            }
//        }

        // Lưu danh sách tên tệp vào MailInfo
        mailInfo.setAttachments(fileNames);

        // Gửi email
        try {
            mailerService.send(mailInfo);
            mailInfo.setStatus(true);  // Đánh dấu là đã gửi email thành công
            model.addAttribute("message", "Email đã được gửi thành công!");
        } catch (Exception e) {
            mailInfo.setStatus(false);
            model.addAttribute("message", "Có lỗi xảy ra khi gửi email: " + e.getMessage());
        }


        model.addAttribute("mailInfo", mailInfo);  // Cập nhật đối tượng MailInfo vào model
        return "redirect:/admin/contact";
    }


}
