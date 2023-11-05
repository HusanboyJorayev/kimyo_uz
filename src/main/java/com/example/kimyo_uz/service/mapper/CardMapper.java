package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Card;
import com.example.kimyo_uz.request.CardRequest;
import com.example.kimyo_uz.response.CardResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public abstract class CardMapper {
    public abstract Card toEntity(CardRequest card);

    public abstract CardResponse toDto(Card card);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Card card, CardRequest request);
}
