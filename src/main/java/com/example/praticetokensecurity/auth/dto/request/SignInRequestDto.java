package com.example.praticetokensecurity.auth.dto.request;

import jakarta.validation.Valid;
import lombok.Getter;

@Getter
public class SignInRequestDto {
    @Valid
    private String email;

    @Valid
    private String password;

}
