package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Basket;
import com.example.kimyo_uz.request.BasketRequest;
import com.example.kimyo_uz.response.BasketResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class BasketMapper {

    @Autowired
    protected CardMapper cardMapper;

    public abstract Basket toEntity(BasketRequest request);

    public abstract BasketResponse toDto(Basket basket);

    @Mapping(target = "cards",expression = ("java(basket.getCards().stream().map(this.cardMapper::toDto).collect(Collectors.toSet()))"))
    public abstract BasketResponse toDtoWihtCard(Basket basket);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Basket basket, BasketRequest request);

    public void view(BasketResponse response,Basket basket){
        response.setCards(basket.getCards().stream().map(this.cardMapper::toDto).collect(Collectors.toSet()));
    }
}
