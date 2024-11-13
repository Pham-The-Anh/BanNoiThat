package com.example.asm_java5.SendMail.Dao;

import com.example.asm_java5.SendMail.Entity.MailInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SendMailDao extends JpaRepository<MailInfo , String> {
}
