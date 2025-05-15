package com.example.praticetokensecurity.auth.controller;

import com.example.praticetokensecurity.auth.dto.request.SignInRequestDto;
import com.example.praticetokensecurity.auth.dto.request.SignUpRequestDto;
import com.example.praticetokensecurity.auth.dto.response.SignInResponseDto;
import com.example.praticetokensecurity.auth.dto.response.SignUpResponseDto;
import com.example.praticetokensecurity.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    ResponseEntity<SignUpResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto){
        SignUpResponseDto response = authService.signUp(signUpRequestDto.getEmail()
                ,signUpRequestDto.getPassword()
                ,signUpRequestDto.getUserRole());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/signin")
    ResponseEntity<SignInResponseDto> singIn(@Valid @RequestBody SignInRequestDto requestDto){
        SignInResponseDto response = authService.signIn(requestDto.getEmail()
                , requestDto.getPassword());
        return ResponseEntity.ok(response);


    }

}
