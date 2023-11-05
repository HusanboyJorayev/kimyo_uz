package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Payment;
import com.example.kimyo_uz.repository.PaymentRepository;
import com.example.kimyo_uz.request.PaymentRequest;
import com.example.kimyo_uz.response.PaymentResponse;
import com.example.kimyo_uz.service.mapper.PaymentMapper;
import com.example.kimyo_uz.service.validation.PaymentValidation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final PaymentValidation paymentValidation;

    public ResponseDto<PaymentResponse> create(@Valid @RequestBody PaymentRequest request) {
        List<ErrorDto> errors = this.paymentValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Payment payment = this.paymentMapper.toEntity(request);
            payment.setCreatedAt(LocalDateTime.now());
            this.paymentRepository.save(payment);

            return ResponseDto.<PaymentResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.paymentMapper.toDto(payment))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }

    public ResponseDto<PaymentResponse> get(@RequestParam Long id) {
        try {
            return this.paymentRepository.findByIdAndDeletedAtIsNull(id)
                    .map(payment ->
                            ResponseDto.<PaymentResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.paymentMapper.toDtoWithOrder(payment))
                                    .build())
                    .orElse(
                            ResponseDto.<PaymentResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<PaymentResponse> update(@Valid @RequestBody PaymentRequest request, @RequestParam Long id) {
        List<ErrorDto> errors = this.paymentValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.paymentRepository.findByIdAndDeletedAtIsNull(id)
                    .map(payment -> {
                        payment.setUpdatedAt(LocalDateTime.now());
                        this.paymentRepository.save(payment);
                        this.paymentMapper.update(payment, request);

                        return ResponseDto.<PaymentResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.paymentMapper.toDto(payment))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<PaymentResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<PaymentResponse> delete(@RequestParam Long id) {
        try {
            return this.paymentRepository.findByIdAndDeletedAtIsNull(id)
                    .map(payment -> {
                        payment.setDeletedAt(LocalDateTime.now());
                        this.paymentRepository.save(payment);

                        return ResponseDto.<PaymentResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.paymentMapper.toDto(payment))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<PaymentResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<PaymentResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<List<PaymentResponse>> getAllByQuery() {
        return Optional.of(this.paymentRepository.getAllByQuery())
                .map(payments -> ResponseDto.<List<PaymentResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(payments.stream().map(this.paymentMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<PaymentResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<PaymentResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        Page<Payment> paymentPage = this.paymentRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (paymentPage.isEmpty()) {
            return ResponseDto.<Page<PaymentResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<PaymentResponse>>builder()
                .success(true)
                .message("Ok")
                .data(paymentPage.map(this.paymentMapper::toDto))
                .build();
    }
}
