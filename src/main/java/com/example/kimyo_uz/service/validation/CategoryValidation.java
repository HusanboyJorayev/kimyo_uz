package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.CategoryRequest;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryValidation {
    public List<ErrorDto> validate(CategoryRequest request) {
        List<ErrorDto> error = new ArrayList<>();

        if (StringUtils.isBlank(request.getName())) {
            error.add(new ErrorDto("categoryName cannot be null or empty", "categoryName"));
        }
        return error;
    }
}
