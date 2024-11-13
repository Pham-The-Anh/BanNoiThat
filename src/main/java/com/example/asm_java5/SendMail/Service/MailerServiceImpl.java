package com.example.asm_java5.SendMail.Service;

import com.example.asm_java5.SendMail.Dao.SendMailDao;
import com.example.asm_java5.SendMail.Entity.MailInfo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailerServiceImpl implements MailerService{
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    SendMailDao sendMailDao;
    @Override
    public void send(MailInfo mail) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage ,true, "utf8");
        mimeMessageHelper.setTo(mail.getToEmail());
        mimeMessageHelper.setFrom(mail.getFromEmail());
        mimeMessageHelper.setSubject(mail.getSubject());
        mimeMessageHelper.setText(mail.getBody(), true);
        mimeMessageHelper.setReplyTo(mail.getFromEmail());
//        String[] cc = mail.get();
//        if(cc != null && cc.length > 0) {
//            mimeMessageHelper.setCc(cc);
//        }
//        String[] bcc = mail.getBcc();
//        if(bcc != null && bcc.length > 0) {
//            mimeMessageHelper.setBcc(bcc);
//        }
//        List<String> attachments = mail.getAttachments(); // Lấy danh sách MultipartFile
//        if (attachments != null && !attachments.isEmpty()) {
//            for (MultipartFile file : attachments) {
//                // Kiểm tra xem tệp có rỗng không
//                if (!file.isEmpty()) {
//                    try {
//                        // Lưu tệp vào một thư mục tạm thời (hoặc làm gì đó khác với tệp)
//                        File tempFile = File.createTempFile("temp-", file.getOriginalFilename());
//                        file.transferTo(tempFile); // Lưu tệp
//
//                        // Thêm tệp vào email
//                        mimeMessageHelper.addAttachment(file.getOriginalFilename(), tempFile);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        // Xử lý ngoại lệ (nếu cần)
//                    }
//                }
//            }
//        }
//        System.out.println("Mail của bạn đã được gửi đi trong giây lát đến: " + mail.getTo());
        MailInfo mailInfo = new MailInfo();
        mailInfo.setFromEmail(mail.getFromEmail());
        mailInfo.setSubject(mail.getSubject());
        mailInfo.setBody(mail.getBody());
        mailInfo.setToEmail(mail.getToEmail());
        mailInfo.setAttachments(mail.getAttachments());


        try {
            mailSender.send(mimeMessage);
            mailInfo.setStatus(true);
        }
        catch (Exception e) {
            mailInfo.setStatus(false); // Gửi thất bại
            throw new MessagingException("Gửi mail thất bại", e);
        }
        sendMailDao.save(mailInfo);
        }
    @Override
    public void send(String to, String subject, String body) throws MessagingException {
        this.send(new MailInfo(to, subject, body));
    }
    List<MailInfo> list = new ArrayList<>();
    @Override
    public void queue(MailInfo mail) {
        list.add(mail);
    }
    @Override
    public void queue(String to, String subject, String body) {
        queue(new MailInfo(to, subject, body));
    }
    @Scheduled(fixedDelay = 5000)
    public void run() {
        while(!list.isEmpty()) {
            MailInfo mail = list.remove(0);
            try {
                this.send(mail);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
