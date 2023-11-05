package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.OrderItem;
import com.example.kimyo_uz.request.OrderItemRequest;
import com.example.kimyo_uz.response.OrderItemResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    public abstract OrderItem toEntity(OrderItemRequest request);

    public abstract OrderItemResponse toDto(OrderItem orderItem);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget OrderItem orderItem, OrderItemRequest request);
}
