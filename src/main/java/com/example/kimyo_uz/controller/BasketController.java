package com.example.kimyo_uz.controller;

import com.example.kimyo_uz.dto.ResponseDto;
import com.example.kimyo_uz.request.BasketRequest;
import com.example.kimyo_uz.response.BasketResponse;
import com.example.kimyo_uz.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.kimyo_uz.swagger.SwaggerConstants.EXAMPLE_RESPONSE_NOT_FOUND;
import static com.example.kimyo_uz.swagger.SwaggerConstants.EXAMPLE_RESPONSE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("basket")
@Tag(name = "Basket",description = "Baskets Api")
public class BasketController {
    private final BasketService basketService;


    @PostMapping("/create")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            description = "Baskets API Success Post Method",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    examples = @ExampleObject(value = EXAMPLE_RESPONSE_SUCCESS)
                            )
                    ),
                    @ApiResponse(
                            description = "Baskets API Not Found Post Method",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    /*schema = @Schema(
                                            implementation = ResponseDto.class
                                    ),*/
                                    examples = @ExampleObject(value = EXAMPLE_RESPONSE_NOT_FOUND)
                            )
                    ),
                    @ApiResponse(
                            description = "Baskets API Not Found Post Method",
                            responseCode = "404",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    /*schema = @Schema(
                                            implementation = ResponseDto.class
                                    ),*/
                                    examples = @ExampleObject(value = EXAMPLE_RESPONSE_NOT_FOUND)
                            )
                    )
            }
    )
    @Operation(summary = "This is product Post method")
    public ResponseDto<BasketResponse> create(@Valid @RequestBody BasketRequest request) {
        return this.basketService.create(request);
    }

    @GetMapping("/get")
    public ResponseDto<BasketResponse> get(@RequestParam Long id) {
        return this.basketService.get(id);
    }

    @PutMapping("/update")
    public ResponseDto<BasketResponse> update(@Valid @RequestBody BasketRequest request, @RequestParam Long id) {
        return this.basketService.update(request, id);
    }

    @DeleteMapping("/delete")
    public ResponseDto<BasketResponse> delete(@RequestParam Long id) {
        return this.basketService.delete(id);
    }

    @GetMapping("/getALl")
    public ResponseDto<List<BasketResponse>> getAllByQuery() {
        return this.basketService.getAllByQuery();
    }

    @GetMapping("/page")
    public ResponseDto<Page<BasketResponse>> getPage(@RequestParam Integer page, @RequestParam Integer size) {
        return this.basketService.getPage(page, size);
    }
}
