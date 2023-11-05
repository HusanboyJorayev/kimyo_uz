package com.example.kimyo_uz.service.mapper;

import com.example.kimyo_uz.entity.Category;
import com.example.kimyo_uz.request.CategoryRequest;
import com.example.kimyo_uz.response.CategoryResponse;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring",imports = Collectors.class)
public abstract class CategoryMapper {

    @Autowired
    protected ProductMapper productMapper;

    public abstract Category toEntity(CategoryRequest request);

    public abstract CategoryResponse toDto(Category category);

    @Mapping(target = "products",expression = "java(category.getProducts().stream().map(this.productMapper::toDto).collect(Collectors.toSet()))")
    public abstract CategoryResponse toDtoWithProduct(Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public abstract void update(@MappingTarget Category category, CategoryRequest request);

    public void view(CategoryResponse response,Category category){
        response.setProducts(category.getProducts().stream().map(this.productMapper::toDto).collect(Collectors.toSet()));
    }
}
