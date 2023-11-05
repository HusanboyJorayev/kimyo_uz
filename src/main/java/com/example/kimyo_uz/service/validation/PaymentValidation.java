package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.PaymentRequest;
import com.example.kimyo_uz.response.PaymentResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentValidation {

    public List<ErrorDto> validate(PaymentRequest request) {
        List<ErrorDto> error = new ArrayList<>();
        if (request.getCustomerId() == null) {
            error.add(new ErrorDto("customerId cannot be null", "customerId"));
        }
        return error;
    }
}
