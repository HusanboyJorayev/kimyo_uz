package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Order;
import com.example.kimyo_uz.request.OrderRequest;
import com.example.kimyo_uz.response.OrderResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class OrderMapper {

    @Autowired
    protected OrderItemMapper orderItemMapper;

    public abstract Order toEntity(OrderRequest request);

    public abstract OrderResponse toDto(Order order);

    @Mapping(target = "orderItems",expression = "java(order.getOrderItems().stream().map(this.orderItemMapper::toDto).collect(Collectors.toSet()))")
    public abstract OrderResponse toDtoWithOrderItem(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Order order, OrderRequest request);

    public void view(OrderResponse response,Order order){
        response.setOrderItems(order.getOrderItems().stream().map(this.orderItemMapper::toDto).collect(Collectors.toSet()));
    }
}
