package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.ProductRequest;
import com.example.kimyo_uz.response.ProductResponse;
import com.example.kimyo_uz.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;

    @PostMapping("/create")
    public ResponseDto<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return this.productService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<ProductResponse> get(@RequestParam Long id) {
        return this.productService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<ProductResponse> update(@Valid @RequestBody ProductRequest request, @RequestParam Long id) {
        return this.productService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<ProductResponse> delete(@RequestParam Long id) {
        return this.productService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<ProductResponse>> getAllByQuery() {
        return this.productService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<ProductResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.productService.getPage(page, size);
    }
}
