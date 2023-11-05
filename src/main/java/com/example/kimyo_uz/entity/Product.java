package com.example.kimyo_uz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produc")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String prodName;
    private String description;
    private String stock;
    private Integer categoryId;

    @OneToMany(mappedBy = "productId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<OrderItem>orderItems;

    @OneToMany(mappedBy = "productId",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Basket>baskets;

    private LocalDateTime createdAt;
    private LocalDateTime udatedAt;
    private LocalDateTime deletedAt;
}
