package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.ProductRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidation {

    public List<ErrorDto> validate(ProductRequest request) {
        List<ErrorDto> error = new ArrayList<>();
        if (StringUtils.isBlank(request.getProdName())) {
            error.add(new ErrorDto("prodName cannot be null  or empty", "prodName"));
        }
        if (request.getCategoryId() == null) {
            error.add(new ErrorDto("categoryId cannot be null", "categoryId"));
        }
        return error;
    }
}
