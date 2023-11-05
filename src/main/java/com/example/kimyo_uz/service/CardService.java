package com.example.kimyo_uz.service;

import com.example.kimyo_uz.dto.ErrorDto;
import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.entity.Card;
import com.example.kimyo_uz.repository.CardRepository;
import com.example.kimyo_uz.request.CardRequest;
import com.example.kimyo_uz.response.CardResponse;
import com.example.kimyo_uz.service.mapper.CardMapper;
import com.example.kimyo_uz.service.validation.CardValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final CardValidation cardValidation;


    public ResponseDto<CardResponse> create(CardRequest res) {

        List<ErrorDto> errors = this.cardValidation.validate(res);
        if (!errors.isEmpty()) {
            return ResponseDto.<CardResponse>builder()
                    .code(-3)
                    .message("Validation error")
                    .build();
        }
        try {
            Card card = this.cardMapper.toEntity(res);
            card.setCreatedAt(LocalDateTime.now());
            this.cardRepository.save(card);

            return ResponseDto.<CardResponse>builder()
                    .success(true)
                    .message("Ok")
                    .data(this.cardMapper.toDto(card))
                    .build();

        } catch (Exception e) {
            return ResponseDto.<CardResponse>builder()
                    .code(-2)
                    .message("card while creating error")
                    .build();
        }
    }

    public ResponseDto<CardResponse> get(Long id) {

        try {
            return this.cardRepository.findByIdAndDeletedAtIsNull(id)
                    .map(card ->
                            ResponseDto.<CardResponse>builder()
                                    .success(true)
                                    .message("OK")
                                    .data(this.cardMapper.toDto(card))
                                    .build())
                    .orElse(
                            ResponseDto.<CardResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build());
        } catch (Exception e) {
            return ResponseDto.<CardResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<CardResponse> update(CardRequest request, Long id) {

        List<ErrorDto> errors = this.cardValidation.validate(request);
        if (!errors.isEmpty()) {
            return ResponseDto.<CardResponse>builder()
                    .code(-3)
                    .message("VAlidation error")
                    .build();
        }

        try {
            return this.cardRepository.findByIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        card.setUpdatedAt(LocalDateTime.now());
                        this.cardRepository.save(card);
                        this.cardMapper.update(card, request);

                        return ResponseDto.<CardResponse>builder()
                                .success(true)
                                .message("OK")
                                .data(this.cardMapper.toDto(card))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CardResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CardResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<CardResponse> delete(Long id) {
        try {
            return this.cardRepository.findByIdAndDeletedAtIsNull(id)
                    .map(card -> {
                        card.setDeletedAt(LocalDateTime.now());
                        this.cardRepository.save(card);

                        return ResponseDto.<CardResponse>builder()
                                .success(true)
                                .message("Ok")
                                .data(this.cardMapper.toDto(card))
                                .build();
                    })
                    .orElse(
                            ResponseDto.<CardResponse>builder()
                                    .code(-1)
                                    .message("Card is not found")
                                    .build()
                    );

        } catch (Exception e) {
            return ResponseDto.<CardResponse>builder()
                    .code(-2)
                    .message("card while getting error")
                    .build();
        }
    }

    public ResponseDto<Page<CardResponse>> getPage(Integer page, Integer size) {
        Page<Card> cardPage = this.cardRepository.findAllByDeletedAtIsNull(PageRequest.of(page, size));
        if (cardPage.isEmpty()) {
            return ResponseDto.<Page<CardResponse>>builder()
                    .code(-1)
                    .message("Cards are not found")
                    .build();
        }
        return ResponseDto.<Page<CardResponse>>builder()
                .success(true)
                .message("Ok")
                .data(cardPage.map(this.cardMapper::toDto))
                .build();
    }

    public ResponseDto<List<CardResponse>> getAllCards() {

        return Optional.of(this.cardRepository.getAllByQuery())
                .map(cards -> ResponseDto.<List<CardResponse>>builder()
                        .success(true)
                        .message("OK")
                        .data(cards.stream().map(this.cardMapper::toDto).toList())
                        .build())
                .orElse(ResponseDto.<List<CardResponse>>builder()
                        .code(-1)
                        .message("Cards are not found")
                        .build());

    }
}
