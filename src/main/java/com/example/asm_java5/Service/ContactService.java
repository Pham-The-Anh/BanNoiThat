package com.example.asm_java5.Service;


import com.example.asm_java5.Dao.AccountDao;
import com.example.asm_java5.Dao.ContactDao;
import com.example.asm_java5.Entity.Account;
import com.example.asm_java5.Entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    @Autowired
    private ContactDao contactDao;
    @Autowired
    private AccountDao accountDao;

    public Contact saveContact(Contact contact) {
        return contactDao.save(contact);
    }

    public List<Contact> getAllContacts() {
        return contactDao.findAll();
    }

    public Optional<Contact> getContactById(Integer id) {
        return contactDao.findById(id);
    }

    public void updateStatus(Integer id, Boolean status) {
        Optional<Contact> contact = contactDao.findById(id);
        contact.ifPresent(c -> {
            c.setStatus(status);
            contactDao.save(c);
        });
    }
}
