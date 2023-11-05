package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.OrderRequest;
import com.example.kimyo_uz.response.OrderResponse;
import com.example.kimyo_uz.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseDto<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return this.orderService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<OrderResponse> get(@RequestParam Long id) {
        return this.orderService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<OrderResponse> update(@Valid @RequestBody OrderRequest request, @RequestParam Long id) {
        return this.orderService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<OrderResponse> delete(@RequestParam Long id) {
        return this.orderService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<OrderResponse>> getAllByQuery() {
        return this.orderService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<OrderResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.orderService.getPage(page, size);
    }
}
