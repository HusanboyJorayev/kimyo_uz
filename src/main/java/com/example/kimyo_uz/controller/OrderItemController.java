package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.OrderItemRequest;
import com.example.kimyo_uz.response.OrderItemResponse;
import com.example.kimyo_uz.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;

    @PostMapping("/create")
    public ResponseDto<OrderItemResponse> create(@Valid @RequestBody OrderItemRequest request) {
        return this.orderItemService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<OrderItemResponse> get(@RequestParam Long id) {
        return this.orderItemService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<OrderItemResponse> update(@Valid @RequestBody OrderItemRequest request, @RequestParam Long id) {
        return this.orderItemService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<OrderItemResponse> delete(@RequestParam Long id) {
        return this.orderItemService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<OrderItemResponse>> getAllByQuery() {
        return this.orderItemService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<OrderItemResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.orderItemService.getPage(page, size);
    }

}
