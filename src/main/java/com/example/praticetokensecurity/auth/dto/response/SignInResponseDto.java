package com.example.praticetokensecurity.auth.dto.response;

import com.example.praticetokensecurity.user.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponseDto {
    String refreshToken;

    public SignInResponseDto(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
