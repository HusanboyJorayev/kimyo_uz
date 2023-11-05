package com.example.kimyo_uz.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketRequest {
    private Double totalPrice;
    private Integer productId;
}
