package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.BasketRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BasketValidation {

    public List<ErrorDto> validate(BasketRequest request) {
        List<ErrorDto> error = new ArrayList<>();
        if (request.getProductId() == null) {
            error.add(new ErrorDto("productId cannot be null", "productId"));
        }
        return error;
    }
}
