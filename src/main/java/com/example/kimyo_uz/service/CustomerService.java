package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Customer;
import com.example.kimyo_uz.repository.CustomerRepository;
import com.example.kimyo_uz.request.CustomerRequest;
import com.example.kimyo_uz.response.CustomerResponse;
import com.example.kimyo_uz.service.mapper.CustomerMapper;
import com.example.kimyo_uz.service.validation.CustomerValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CustomerValidation customerValidation;

    public ResponseDto<CustomerResponse> create(CustomerRequest request) {
        List<ErrorDto> errors = this.customerValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Customer customer = this.customerMapper.toEntity(request);
            customer.setCreatedAt(LocalDateTime.now());
            this.customerRepository.save(customer);

            return ResponseDto.<CustomerResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.customerMapper.toDto(customer))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }

    public ResponseDto<CustomerResponse> get(Long id) {

        try {
            return this.customerRepository.findByIdAndDeletedAtIsNull(id)
                    .map(card ->
                            ResponseDto.<CustomerResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.customerMapper.toDtoWithCardAndPaymentAndOrder(card))
                                    .build())
                    .orElse(
                            ResponseDto.<CustomerResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<CustomerResponse> update(CustomerRequest request, Long id) {

        List<ErrorDto> errors = this.customerValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.customerRepository.findByIdAndDeletedAtIsNull(id)
                    .map(customer -> {
                        customer.setUpdatedAt(LocalDateTime.now());
                        this.customerRepository.save(customer);
                        this.customerMapper.update(customer, request);

                        return ResponseDto.<CustomerResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.customerMapper.toDto(customer))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CustomerResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<CustomerResponse> delete(Long id) {
        try {
            return this.customerRepository.findByIdAndDeletedAtIsNull(id)
                    .map(customer -> {
                        customer.setDeletedAt(LocalDateTime.now());
                        this.customerRepository.save(customer);

                        return ResponseDto.<CustomerResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.customerMapper.toDto(customer))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CustomerResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CustomerResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<List<CustomerResponse>> getAllByQuery() {
        return Optional.of(this.customerRepository.getAllByQuery())
                .map(customers -> ResponseDto.<List<CustomerResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(customers.stream().map(this.customerMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<CustomerResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }

    public ResponseDto<Page<CustomerResponse>> getPage(Integer page, Integer size) {
        Page<Customer> customerPage = this.customerRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (customerPage.isEmpty()) {
            return ResponseDto.<Page<CustomerResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<CustomerResponse>>builder()
                .success(true)
                .message("Ok")
                .data(customerPage.map(this.customerMapper::toDto))
                .build();
    }
}
