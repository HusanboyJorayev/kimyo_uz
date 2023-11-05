package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.CategoryRequest;
import com.example.kimyo_uz.response.CategoryResponse;
import com.example.kimyo_uz.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/create")
    public ResponseDto<CategoryResponse> create(@Valid @RequestBody CategoryRequest request) {
        return this.categoryService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<CategoryResponse> get(@RequestParam Long id) {
        return this.categoryService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<CategoryResponse> update(@Valid @RequestBody CategoryRequest request, @RequestParam Long id) {
        return this.categoryService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<CategoryResponse> delete(@RequestParam Long id) {
        return this.categoryService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<CategoryResponse>> getAllByQuery() {
        return this.categoryService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<CategoryResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.categoryService.getPage(page, size);
    }
}
