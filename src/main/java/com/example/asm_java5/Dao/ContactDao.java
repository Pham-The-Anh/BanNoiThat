package com.example.asm_java5.Dao;

import com.example.asm_java5.Entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactDao extends JpaRepository<Contact, Integer> {
    List<Contact> findByStatusFalse();
}
