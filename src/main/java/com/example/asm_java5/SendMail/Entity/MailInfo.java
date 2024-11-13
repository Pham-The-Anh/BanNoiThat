package com.example.asm_java5.SendMail.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Mail")
public class MailInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "from_email")
    private String fromEmail;
    @Column(name = "to_email")
    private String toEmail;
    @Column(name = "subject")
    private String subject;
    @Column(name = "body")
    private String body;
    @Column(name = "Date_Create")
    private LocalDateTime sentAt = LocalDateTime.now();
    @ElementCollection
    @Column(name = "attachments")
    private List<String> attachments; // Chuyển đổi từ String[] sang List<MultipartFile>
    @Column(name = "status")
    private boolean status;  // true: thành công, false: thất bại
    public MailInfo(String to, String subject, String body) {
        this.fromEmail = "FPT Polytechnic <vonguyenduytan12cb9@gmail.com>"; // giá trị mặc định
        this.toEmail = to;
        this.subject = subject;
        this.body = body;
    }
}
