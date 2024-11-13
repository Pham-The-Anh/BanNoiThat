package com.example.asm_java5.Dao;

import com.example.asm_java5.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountDao extends JpaRepository<Account, String> {
    Account findByEmail(String email);
}
