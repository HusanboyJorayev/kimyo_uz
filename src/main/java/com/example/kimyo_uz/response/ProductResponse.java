package com.example.kimyo_uz.response;

import com.example.kimyo_uz.entity.Basket;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResponse {
    private Long id;
    @NotBlank
    private String prodName;
    private String description;
    private String stock;
    @NotNull
    private Integer categoryId;

    private Set<OrderItemResponse> orderItems;
    private Set<BasketResponse>baskets;


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
