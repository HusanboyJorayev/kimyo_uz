package com.example.kimyo_uz.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private Double quantity;
    private Double price;
    private Integer productId;
    private Integer orderId;
}
