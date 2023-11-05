package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;

import com.example.kimyo_uz.request.PaymentRequest;
import com.example.kimyo_uz.response.PaymentResponse;
import com.example.kimyo_uz.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {
    private final PaymentService paymentService;


    @PostMapping("/create")
    public ResponseDto<PaymentResponse> create(@Valid @RequestBody PaymentRequest request) {
        return this.paymentService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<PaymentResponse> get(@RequestParam Long id) {
        return this.paymentService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<PaymentResponse> update(@Valid @RequestBody PaymentRequest request, @RequestParam Long id) {
        return this.paymentService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<PaymentResponse> delete(@RequestParam Long id) {
        return this.paymentService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<PaymentResponse>> getAllByQuery() {
        return this.paymentService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<PaymentResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.paymentService.getPage(page, size);
    }
}
