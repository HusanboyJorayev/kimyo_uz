package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Category;
import com.example.kimyo_uz.repository.CategoryRepository;
import com.example.kimyo_uz.request.CategoryRequest;
import com.example.kimyo_uz.response.CategoryResponse;
import com.example.kimyo_uz.service.mapper.CategoryMapper;
import com.example.kimyo_uz.service.validation.CategoryValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryValidation categoryValidation;
    private final CategoryRepository categoryRepository;


    public ResponseDto<CategoryResponse> create(CategoryRequest request) {
        List<ErrorDto> errors = this.categoryValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Category category = this.categoryMapper.toEntity(request);
            category.setCreatedAt(LocalDateTime.now());
            this.categoryRepository.save(category);

            return ResponseDto.<CategoryResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.categoryMapper.toDto(category))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }

    public ResponseDto<CategoryResponse> get(Long id) {
        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category ->
                            ResponseDto.<CategoryResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.categoryMapper.toDtoWithProduct(category))
                                    .build())
                    .orElse(
                            ResponseDto.<CategoryResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<CategoryResponse> update(CategoryRequest request, Long id) {
        List<ErrorDto> errors = this.categoryValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }

        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category -> {
                        category.setUpdatedAt(LocalDateTime.now());
                        this.categoryRepository.save(category);
                        this.categoryMapper.update(category, request);

                        return ResponseDto.<CategoryResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.categoryMapper.toDto(category))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CategoryResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<CategoryResponse> delete(Long id) {
        try {
            return this.categoryRepository.findByIdAndDeletedAtIsNull(id)
                    .map(category -> {
                        category.setDeletedAt(LocalDateTime.now());
                        this.categoryRepository.save(category);

                        return ResponseDto.<CategoryResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.categoryMapper.toDto(category))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CategoryResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CategoryResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }


    public ResponseDto<List<CategoryResponse>> getAllByQuery() {
        return Optional.of(this.categoryRepository.getAllByQuery())
                .map(categories -> ResponseDto.<List<CategoryResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(categories.stream().map(this.categoryMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<CategoryResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());
    }


    public ResponseDto<Page<CategoryResponse>> getPage(Integer page, Integer size) {
        Page<Category> categoryPage = this.categoryRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (categoryPage.isEmpty()) {
            return ResponseDto.<Page<CategoryResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<CategoryResponse>>builder()
                .success(true)
                .message("Ok")
                .data(categoryPage.map(this.categoryMapper::toDto))
                .build();
    }
}
