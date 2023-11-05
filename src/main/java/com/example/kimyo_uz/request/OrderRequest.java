package com.example.kimyo_uz.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private Double totalPrice;
    private Date orderDate;
    private Integer customerId;
    private Integer paymentId;
}
