package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Payment;
import com.example.kimyo_uz.request.PaymentRequest;
import com.example.kimyo_uz.response.PaymentResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class PaymentMapper {

    @Autowired
    protected OrderMapper orderMapper;

    public abstract Payment toEntity(PaymentRequest request);

    public abstract PaymentResponse toDto(Payment payment);

    @Mapping(target = "orders",expression = "java(payment.getOrders().stream().map(this.orderMapper::toDto).collect(Collectors.toSet()))")
    public abstract PaymentResponse toDtoWithOrder(Payment payment);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Payment payment, PaymentRequest request);

    public void view(PaymentResponse response,Payment payment){
        response.setOrders(payment.getOrders().stream().map(this.orderMapper::toDto).collect(Collectors.toSet()));
    }
}
