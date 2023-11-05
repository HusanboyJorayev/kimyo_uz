package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.CustomerRequest;
import com.example.kimyo_uz.response.CustomerResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomerValidation {

    public List<ErrorDto> validate(CustomerRequest response) {
        List<ErrorDto> error = new ArrayList<>();
        if (StringUtils.isBlank(response.getEmail())) {
            error.add(new ErrorDto("email cannot be null or mepty", "email"));
        }
        if (StringUtils.isBlank(response.getFirstname())) {
            error.add(new ErrorDto("firstname cannot be null or mepty", "email"));
        }
        if (StringUtils.isBlank(response.getLastname())) {
            error.add(new ErrorDto("lastname cannot be null or mepty", "email"));
        }
        if (StringUtils.isBlank(response.getPassword())) {
            error.add(new ErrorDto("password cannot be null or mepty", "email"));
        }
        return error;
    }
}
