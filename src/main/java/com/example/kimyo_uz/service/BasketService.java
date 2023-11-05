package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Basket;
import com.example.kimyo_uz.repository.BasketRepository;
import com.example.kimyo_uz.request.BasketRequest;
import com.example.kimyo_uz.response.BasketResponse;
import com.example.kimyo_uz.service.mapper.BasketMapper;
import com.example.kimyo_uz.service.validation.BasketValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketMapper basketMapper;
    private final BasketValidation basketValidation;
    private final BasketRepository basketRepository;


    public ResponseDto<BasketResponse> create(BasketRequest request) {
        List<ErrorDto> errors = this.basketValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Basket basket = this.basketMapper.toEntity(request);
            basket.setCreatedAt(LocalDateTime.now());
            this.basketRepository.save(basket);

            return ResponseDto.<BasketResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.basketMapper.toDto(basket))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }

    public ResponseDto<BasketResponse> get(Long id) {
        try {
            return this.basketRepository.findByIdAndDeletedAtIsNull(id)
                    .map(basket ->
                            ResponseDto.<BasketResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.basketMapper.toDtoWihtCard(basket))
                                    .build())
                    .orElse(
                            ResponseDto.<BasketResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<BasketResponse> update(BasketRequest request, Long id) {
        List<ErrorDto> errors = this.basketValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.basketRepository.findByIdAndDeletedAtIsNull(id)
                    .map(basket -> {
                        basket.setUpdatedAt(LocalDateTime.now());
                        this.basketRepository.save(basket);
                        this.basketMapper.update(basket, request);

                        return ResponseDto.<BasketResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.basketMapper.toDto(basket))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<BasketResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<BasketResponse> delete(Long id) {
        try {
            return this.basketRepository.findByIdAndDeletedAtIsNull(id)
                    .map(basket -> {
                        basket.setDeletedAt(LocalDateTime.now());
                        this.basketRepository.save(basket);

                        return ResponseDto.<BasketResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.basketMapper.toDto(basket))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<BasketResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<BasketResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<List<BasketResponse>> getAllByQuery() {
        return Optional.of(this.basketRepository.getAllByQuery())
                .map(baskets -> ResponseDto.<List<BasketResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(baskets.stream().map(this.basketMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<BasketResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<BasketResponse>> getPage(Integer page, Integer size) {
        Page<Basket> basketPage = this.basketRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (basketPage.isEmpty()) {
            return ResponseDto.<Page<BasketResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<BasketResponse>>builder()
                .success(true)
                .message("Ok")
                .data(basketPage.map(this.basketMapper::toDto))
                .build();
    }
}
