package com.example.asm_java5.Service;

import com.example.asm_java5.Dao.AccountDao;
import com.example.asm_java5.Entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountDao dao;

    public Account findAccountByEmail(String email) {
        return dao.findByEmail(email);
    }
    public Account login(String email, String password) {
        Account account = dao.findByEmail(email);
        if (account != null && account.getPassword().equals(password)) {
            return account; // Trả về tài khoản nếu mật khẩu đúng
        }
        return null; // Trả về null nếu không tìm thấy tài khoản hoặc mật khẩu sai
    }
    public boolean emailExists(String email) {
        return dao.findByEmail(email) != null; // Kiểm tra email có tồn tại
    }

    public void registerAccount(Account account) {
        account.setAdmin(false); // Đặt vai trò mặc định là user
        dao.save(account); // Lưu tài khoản vào cơ sở dữ liệu
    }
    public List<Account> getAllAccounts() {
        return dao.findAll();
    }

    // Lấy tất cả tài khoản
    public List<Account> findAll() {
        return dao.findAll();
    }

    // Lấy tài khoản theo id
    public Optional<Account> findById(String email) {
        return dao.findById(email);
    }


    // Cập nhật tài khoản
    public Account update(Account account) {
        return dao.save(account);
    }

    // Xóa tài khoản theo id
    public void deleteById(String email) {
        if (dao.existsById(email)) {
            dao.deleteById(email);
        }
    }
}
