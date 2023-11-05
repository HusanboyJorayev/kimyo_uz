package com.example.kimyo_uz.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardRequest {
    @NotBlank(message = "cardName cannot be null or empty")
    private String name;
    @NotBlank(message = "cardNumber cannot be null or empty")
    private String number;
    private Double quantity;
    private Integer customerId;
    private Integer basketId;
}
