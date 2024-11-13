package com.example.asm_java5.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "Email")
    private Account account;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Message")
    private String message;
    @Column(name = "Created_At")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "Status")
    private boolean status;  // true là đã check, false là chưa check
}
