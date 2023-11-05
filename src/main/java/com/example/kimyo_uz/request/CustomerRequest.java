package com.example.kimyo_uz.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    private String lastname;
    private String firstname;
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
}
