package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.OrderRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderValidation {

    public List<ErrorDto>validate(OrderRequest request){
        List<ErrorDto>error=new ArrayList<>();

        if (request.getCustomerId()==null){
            error.add(new ErrorDto("customerId cannot be null or empty","customerId"));
        }
        return error;
    }
}
