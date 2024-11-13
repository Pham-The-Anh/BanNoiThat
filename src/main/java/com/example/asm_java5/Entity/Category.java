package com.example.asm_java5.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @Column(name = "ID")
    String id;
    @Column(name = "Name")
    String name;
    @OneToMany(mappedBy = "category")
    List<Product> products;
}
