package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Product;
import com.example.kimyo_uz.request.ProductRequest;
import com.example.kimyo_uz.response.ProductResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring", imports = Collectors.class)
public abstract class ProductMapper {

    @Autowired
    protected OrderItemMapper orderItemMapper;
    @Autowired
    protected BasketMapper basketMapper;

    public abstract Product toEntity(ProductRequest request);

    public abstract ProductResponse toDto(Product product);

    @Mapping(target = "orderItems", expression = "java(product.getOrderItems().stream().map(this.orderItemMapper::toDto).collect(Collectors.toSet()))")
    @Mapping(target = "baskets", expression = "java(product.getBaskets().stream().map(this.basketMapper::toDto).collect(Collectors.toSet()))")
    public abstract ProductResponse toDtoWithOrderItemAndBAsket(Product product);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Product product, ProductRequest request);

    public void view(ProductResponse response, Product product) {
        response.setOrderItems(product.getOrderItems().stream().map(this.orderItemMapper::toDto).collect(Collectors.toSet()));
        response.setBaskets(product.getBaskets().stream().map(this.basketMapper::toDto).collect(Collectors.toSet()));
    }
}
