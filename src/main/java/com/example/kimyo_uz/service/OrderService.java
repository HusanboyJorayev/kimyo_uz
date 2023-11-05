package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Order;
import com.example.kimyo_uz.repository.OrderRepository;
import com.example.kimyo_uz.request.OrderRequest;
import com.example.kimyo_uz.response.OrderResponse;
import com.example.kimyo_uz.service.mapper.OrderMapper;
import com.example.kimyo_uz.service.validation.OrderValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderMapper orderMapper;
    private final OrderValidation orderValidation;
    private final OrderRepository orderRepository;


    public ResponseDto<OrderResponse> create(OrderRequest request) {
        List<ErrorDto> errors = this.orderValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Order order = this.orderMapper.toEntity(request);
            order.setCreatedAt(LocalDateTime.now());
            this.orderRepository.save(order);

            return ResponseDto.<OrderResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.orderMapper.toDto(order))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }


    public ResponseDto<OrderResponse> get(Long id) {
        try {
            return this.orderRepository.findByIdAndDeletedAtIsNull(id)
                    .map(order ->
                            ResponseDto.<OrderResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.orderMapper.toDtoWithOrderItem(order))
                                    .build())
                    .orElse(
                            ResponseDto.<OrderResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<OrderResponse> update(OrderRequest request, Long id) {
        List<ErrorDto> errors = this.orderValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.orderRepository.findByIdAndDeletedAtIsNull(id)
                    .map(order -> {
                        order.setUpdatedAt(LocalDateTime.now());
                        this.orderRepository.save(order);
                        this.orderMapper.update(order, request);

                        return ResponseDto.<OrderResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.orderMapper.toDto(order))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<OrderResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<OrderResponse> delete(Long id) {
        try {
            return this.orderRepository.findByIdAndDeletedAtIsNull(id)
                    .map(order -> {
                        order.setDeletedAt(LocalDateTime.now());
                        this.orderRepository.save(order);
                        return ResponseDto.<OrderResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.orderMapper.toDto(order))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<OrderResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<OrderResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<List<OrderResponse>> getAllByQuery() {
        return Optional.of(this.orderRepository.getAllByQuery())
                .map(orders -> ResponseDto.<List<OrderResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(orders.stream().map(this.orderMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<OrderResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<OrderResponse>> getPage(Integer page, Integer size) {
        Page<Order> orderPage = this.orderRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (orderPage.isEmpty()) {
            return ResponseDto.<Page<OrderResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<OrderResponse>>builder()
                .success(true)
                .message("Ok")
                .data(orderPage.map(this.orderMapper::toDto))
                .build();
    }
}
