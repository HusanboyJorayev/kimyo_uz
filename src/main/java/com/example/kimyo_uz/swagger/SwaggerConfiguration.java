package com.example.kimyo_uz.swagger;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi apiBasket() {
        return GroupedOpenApi.builder()
                .group("Basket")
                .pathsToMatch("/basket/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiCard() {
        return GroupedOpenApi.builder()
                .group("Card")
                .pathsToMatch("/card/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiCategory() {
        return GroupedOpenApi.builder()
                .group("Category")
                .pathsToMatch("/category/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiCustomer() {
        return GroupedOpenApi.builder()
                .group("Customer")
                .pathsToMatch("/customer/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiOrder() {
        return GroupedOpenApi.builder()
                .group("Orders")
                .pathsToMatch("/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiOrderItem() {
        return GroupedOpenApi.builder()
                .group("OrderItem")
                .pathsToMatch("/orderItem/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiPayment() {
        return GroupedOpenApi.builder()
                .group("Payment")
                .pathsToMatch("/payment/**")
                .build();
    }

    @Bean
    public GroupedOpenApi apiProduct() {
        return GroupedOpenApi.builder()
                .group("Product")
                .pathsToMatch("/product/**")
                .build();
    }
}
