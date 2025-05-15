package com.example.praticetokensecurity.auth.service;

import com.example.praticetokensecurity.auth.dto.response.SignInResponseDto;
import com.example.praticetokensecurity.auth.dto.response.SignUpResponseDto;
import com.example.praticetokensecurity.config.JwtTokenProvider;
import com.example.praticetokensecurity.token.entity.RefreshToken;
import com.example.praticetokensecurity.token.repository.RefreshTokenRepository;
import com.example.praticetokensecurity.token.service.RefreshTokenService;
import com.example.praticetokensecurity.user.entity.User;
import com.example.praticetokensecurity.user.enums.UserRole;
import com.example.praticetokensecurity.user.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;

    public SignUpResponseDto signUp(String email, String password, String Role) {

        if (userRepository.existsByEmail(email)){
            throw new RuntimeException("존재유저"); // 임시 에러처리
        }

        UserRole userRole = UserRole.of(Role);
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.of(email,encodedPassword,userRole);
        userRepository.save(user);

        return SignUpResponseDto.from(email, userRole);

    }

    public SignInResponseDto signIn(@Valid String email, @Valid String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않은 유저"));

        if(!passwordEncoder.matches(password, user.getPassword())){
            new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        RefreshToken savedRefreshToken = refreshTokenService.saveToken(refreshToken, user.getId());

        return new SignInResponseDto(savedRefreshToken.getToken());
    }
}
