package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.OrderItem;
import com.example.kimyo_uz.repository.OrderItemRepository;
import com.example.kimyo_uz.request.OrderItemRequest;
import com.example.kimyo_uz.response.OrderItemResponse;
import com.example.kimyo_uz.service.mapper.OrderItemMapper;
import com.example.kimyo_uz.service.validation.OrderItemValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemValidation orderItemValidation;
    private final OrderItemMapper orderItemMapper;


    public ResponseDto<OrderItemResponse> create(OrderItemRequest request) {
        List<ErrorDto> errors = this.orderItemValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            OrderItem orderItem = this.orderItemMapper.toEntity(request);
            orderItem.setCreatedAt(LocalDateTime.now());
            this.orderItemRepository.save(orderItem);

            return ResponseDto.<OrderItemResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.orderItemMapper.toDto(orderItem))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }


    public ResponseDto<OrderItemResponse> get(Long id) {
        try {
            return this.orderItemRepository.findByIdAndDeletedAtIsNull(id)
                    .map(orderItem ->
                            ResponseDto.<OrderItemResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.orderItemMapper.toDto(orderItem))
                                    .build())
                    .orElse(
                            ResponseDto.<OrderItemResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<OrderItemResponse> update(OrderItemRequest request, Long id) {
        List<ErrorDto> errors = this.orderItemValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.orderItemRepository.findByIdAndDeletedAtIsNull(id)
                    .map(orderItem -> {
                        orderItem.setUpdatedAt(LocalDateTime.now());
                        this.orderItemRepository.save(orderItem);
                        this.orderItemMapper.update(orderItem, request);

                        return ResponseDto.<OrderItemResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.orderItemMapper.toDto(orderItem))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<OrderItemResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<OrderItemResponse> delete(Long id) {
        try {
            return this.orderItemRepository.findByIdAndDeletedAtIsNull(id)
                    .map(orderItem -> {
                        orderItem.setDeletedAt(LocalDateTime.now());
                        this.orderItemRepository.save(orderItem);

                        return ResponseDto.<OrderItemResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.orderItemMapper.toDto(orderItem))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<OrderItemResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<OrderItemResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<List<OrderItemResponse>> getAllByQuery() {
        return Optional.of(this.orderItemRepository.getAllByQuery())
                .map(orderItems -> ResponseDto.<List<OrderItemResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(orderItems.stream().map(this.orderItemMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<OrderItemResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<OrderItemResponse>> getPage(Integer page, Integer size) {
        Page<OrderItem> orderItemPage = this.orderItemRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (orderItemPage.isEmpty()) {
            return ResponseDto.<Page<OrderItemResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<OrderItemResponse>>builder()
                .success(true)
                .message("Ok")
                .data(orderItemPage.map(this.orderItemMapper::toDto))
                .build();
    }
}
