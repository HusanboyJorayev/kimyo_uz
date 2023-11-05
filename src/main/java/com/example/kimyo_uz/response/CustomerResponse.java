package com.example.kimyo_uz.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private Long id;
    @NotBlank(message = "lastname cannot be null or empty")
    private String lastname;
    @NotBlank(message = "firstname cannot be null or empty")
    private String firstname;
    @NotBlank(message = "email cannot be null or empty")
    private String email;
    @NotBlank(message = "password cannot be null or empty")
    private String password;
    private String address;
    private String phoneNumber;


    private Set<CardResponse> cards;
    private Set<PaymentResponse> payments;
    private Set<OrderResponse> orders;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
