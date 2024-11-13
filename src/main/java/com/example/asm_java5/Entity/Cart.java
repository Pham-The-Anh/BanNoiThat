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
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "Account_ID")
    private Account account;
    @ManyToOne
    @JoinColumn(name = "Product_ID")
    Product product;
    @Column(name="Quantity")
    private Integer quantity;
    @Column(name = "Date_Added")
    private LocalDateTime dateTime = LocalDateTime.now();
    @Transient // Không lưu vào database
    private String formattedPrice;
}
