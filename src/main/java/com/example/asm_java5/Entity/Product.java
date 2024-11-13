package com.example.asm_java5.Entity;

import jakarta.persistence.*;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import lombok.Data;

@Data
@Entity
@Table(name = "Products")
public class Product {
    @Id
    @Column(name="ID")
    String productID;
    @Column(name = "Tiltle")  // Ánh xạ với cột ProductName trong database
    String productName;
    @Column(name="Image")
    String image;
    @Column(name="Price")
    Integer price;
    @Transient // Không lưu vào database
    private String formattedPrice;
    @Column(name="Description")
    String Description;
    @ManyToOne
    @JoinColumn(name = "Category")
    Category category;
//    @OneToMany(mappedBy = "product")
//    List<OrderDetail> orderDetails;

    public String getFormattedPrice() {
        if (formattedPrice == null) {
            formattedPrice = formatCurrency(this.price); // Định dạng lại nếu giá trị bị null
        }
        return formattedPrice;
    }

    public static String formatCurrency(double price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        return formatter.format(price);
    }
}
