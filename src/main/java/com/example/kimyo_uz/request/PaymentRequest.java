package com.example.kimyo_uz.request;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    private Integer customerId;
    private Date paymentDate;
    private Double amount;
}
