package com.example.asm_java5.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @Column(nullable = false, length = 100 , name = "Email")
    private String email;  // Sử dụng Email làm khóa chính

    @Column(nullable = false, length = 100 , name = "Fullname")
    private String fullname;

    @Column(length = 20 , name = "Phone")
    private String phone;

    @Column(length = 250 , name = "Photo")
    private String photo;

    @Column(length = 255 , name = "Address")
    private String address;

    @Column(length = 100 , name = "City")
    private String city;

    @Column(length = 100 , name = "Country")
    private String country;

    @Column(nullable = false, length = 255 , name = "Password")
    private String password;
    @Column(name = "Admin")
    private boolean admin = false;  // Vai trò: true = admin, false = user

    @Column(length = 10 , name = "Gender")
    private String gender;
//
    @Column(name = "Date_Create")
    private LocalDateTime createAccount;

}
