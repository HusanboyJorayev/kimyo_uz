package com.example.kimyo_uz.service.validation;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.request.OrderItemRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderItemValidation {

    public List<ErrorDto>validate(OrderItemRequest request){
        List<ErrorDto>error=new ArrayList<>();

        if (request.getOrderId()==null){
            error.add(new ErrorDto("orderId cannot be null","orderId"));
        }
        if (request.getProductId()==null){
            error.add(new ErrorDto("productID cannot be null","productID"));
        }
        if (request.getPrice()==null){
            error.add(new ErrorDto("price cannot be null","price"));
        }
        return error;
    }
}
