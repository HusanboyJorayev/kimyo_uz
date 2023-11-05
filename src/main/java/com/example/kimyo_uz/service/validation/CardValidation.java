package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.CardRequest;
import com.example.kimyo_uz.response.CardResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardValidation {
    public List<ErrorDto> validate(CardRequest response) {
        List<ErrorDto> errors = new ArrayList<>();
        if (StringUtils.isBlank(response.getName())) {
            errors.add(new ErrorDto("cardName cannot be null or empty", "cardName"));
        }
        if (response.getNumber() == null) {
            errors.add(new ErrorDto("cardNumber cannot be null", "cardNumber"));
        }
        return errors;
    }
}
