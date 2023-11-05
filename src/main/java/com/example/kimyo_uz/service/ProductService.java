package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Product;
import com.example.kimyo_uz.repository.ProductRepository;
import com.example.kimyo_uz.request.ProductRequest;
import com.example.kimyo_uz.response.ProductResponse;
import com.example.kimyo_uz.service.mapper.ProductMapper;
import com.example.kimyo_uz.service.validation.ProductValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final ProductValidation productValidation;


    public ResponseDto<ProductResponse> create(ProductRequest request) {
        List<ErrorDto> errors = this.productValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Product product = this.productMapper.toEntity(request);
            product.setCreatedAt(LocalDateTime.now());
            this.productRepository.save(product);

            return ResponseDto.<ProductResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.productMapper.toDto(product))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }


    public ResponseDto<ProductResponse> get(Long id) {
        try {
            return this.productRepository.findByIdAndDeletedAtIsNull(id)
                    .map(product ->
                            ResponseDto.<ProductResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.productMapper.toDtoWithOrderItemAndBAsket(product))
                                    .build())
                    .orElse(
                            ResponseDto.<ProductResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<ProductResponse> update(ProductRequest request, Long id) {
        List<ErrorDto> errors = this.productValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.productRepository.findByIdAndDeletedAtIsNull(id)
                    .map(product -> {
                        product.setUdatedAt(LocalDateTime.now());
                        this.productRepository.save(product);
                        this.productMapper.update(product, request);

                        return ResponseDto.<ProductResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.productMapper.toDto(product))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<ProductResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<ProductResponse> delete(Long id) {
        try {
            return this.productRepository.findByIdAndDeletedAtIsNull(id)
                    .map(product -> {
                        product.setDeletedAt(LocalDateTime.now());
                        this.productRepository.save(product);

                        return ResponseDto.<ProductResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.productMapper.toDto(product))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<ProductResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<ProductResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<List<ProductResponse>> getAllByQuery() {
        return Optional.of(this.productRepository.getAllByQuery())
                .map(customers -> ResponseDto.<List<ProductResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(customers.stream().map(this.productMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<ProductResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<ProductResponse>> getPage(Integer page, Integer size) {
        Page<Product> productPage = this.productRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (productPage.isEmpty()) {
            return ResponseDto.<Page<ProductResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<ProductResponse>>builder()
                .success(true)
                .message("Ok")
                .data(productPage.map(this.productMapper::toDto))
                .build();
    }
}
