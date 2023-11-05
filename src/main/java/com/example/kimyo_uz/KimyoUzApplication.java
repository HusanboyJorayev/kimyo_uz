package com.example.kimyo_uz;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                version = "3.0.11",
                contact = @Contact(
                        name = "Husanboy",
                        email = "hjorayev@gmail.com"
                )
        )
)
public class KimyoUzApplication {

    public static void main(String[] args) {
        SpringApplication.run(KimyoUzApplication.class, args);
    }

}
