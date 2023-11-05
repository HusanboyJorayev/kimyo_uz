package com.example.kimyo_uz.exception;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionClass {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> exception(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                ResponseDto.<Void>builder()
                        .code(-3)
                        .message("Validation error")
                        .errors(
                                e.getBindingResult()
                                        .getFieldErrors()
                                        .stream()
                                        .map(fieldError -> {
                                            String message = fieldError.getDefaultMessage();
                                            String field = fieldError.getField();

                                            return new ErrorDto(message, field);
                                        }).toList()
                        )
                        .build()
        );
    }
}
