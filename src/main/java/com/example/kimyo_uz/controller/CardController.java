package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.CardRequest;
import com.example.kimyo_uz.response.CardResponse;
import com.example.kimyo_uz.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "card")
public class  CardController {

    private final CardService cardService;


    @PostMapping("/create")
    public ResponseDto<CardResponse> create(@Valid @RequestBody CardRequest response) {
        return this.cardService.create(response);
    }


    @GetMapping("/get")
    public ResponseDto<CardResponse> get(@RequestParam Long id) {
        return this.cardService.get(id);
    }


    @PutMapping("/update")
    public ResponseDto<CardResponse> update(@Valid @RequestBody CardRequest request, @RequestParam Long id) {
        return this.cardService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<CardResponse> delete(@RequestParam Long id) {
        return this.cardService.delete(id);
    }

    @GetMapping("/getPage")
    public ResponseDto<Page<CardResponse>> getPage(@RequestParam Integer size, @RequestParam Integer page) {
        return this.cardService.getPage(page, size);
    }

    @GetMapping("/getAllCards")
    public ResponseDto<List<CardResponse>> getAllCards() {
        return this.cardService.getAllCards();
    }
}
