package com.example.kimyo_uz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double totalPrice;
    private Date orderDate;
    private Integer customerId;
    private Integer paymentId;

    @OneToMany(mappedBy = "orderId",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private Set<OrderItem>orderItems;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
