package com.example.kimyo_uz.service.mapper;


import com.example.kimyo_uz.entity.Customer;
import com.example.kimyo_uz.request.CustomerRequest;
import com.example.kimyo_uz.response.CustomerResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class CustomerMapper {

    @Autowired
    protected CardMapper cardMapper;
    @Autowired
    protected PaymentMapper paymentMapper;
    @Autowired
    protected OrderMapper orderMapper;

    public abstract Customer toEntity(CustomerRequest request);

    public abstract CustomerResponse toDto(Customer customer);

    @Mapping(target = "cards",expression = "java(customer.getCards().stream().map(this.cardMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "payments",expression = "java(customer.getPayments().stream().map(this.paymentMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "orders",expression = "java(customer.getOrders().stream().map(this.orderMapper::toDto).collect(Collectors.toSet()))")
    public abstract CustomerResponse toDtoWithCardAndPaymentAndOrder(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Customer customer, CustomerRequest request);

    public void view(Customer customer,CustomerResponse response){
        response.setCards(customer.getCards().stream().map(this.cardMapper::toDto).collect(Collectors.toSet()));
        response.setPayments(customer.getPayments().stream().map(this.paymentMapper::toDtoWithOrder).collect(Collectors.toSet()));
        response.setOrders(customer.getOrders().stream().map(this.orderMapper::toDtoWithOrderItem).collect(Collectors.toSet()));
    }
}
