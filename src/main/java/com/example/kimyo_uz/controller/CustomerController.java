package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.CustomerRequest;
import com.example.kimyo_uz.response.CustomerResponse;
import com.example.kimyo_uz.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseDto<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return this.customerService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<CustomerResponse> get(@RequestParam Long id) {
        return this.customerService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<CustomerResponse> update(@Valid @RequestBody CustomerRequest request, @RequestParam Long id) {
        return this.customerService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<CustomerResponse> delete(@RequestParam Long id) {
        return this.customerService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<CustomerResponse>> getAllByQuery() {
        return this.customerService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<CustomerResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.customerService.getPage(page, size);
    }
}
