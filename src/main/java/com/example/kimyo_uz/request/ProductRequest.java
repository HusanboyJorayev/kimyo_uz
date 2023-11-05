package com.example.kimyo_uz.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String prodName;
    private String description;
    private String stock;
    private Integer categoryId;
}
