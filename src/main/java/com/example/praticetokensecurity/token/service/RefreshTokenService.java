package com.example.praticetokensecurity.token.service;

import com.example.praticetokensecurity.token.entity.RefreshToken;
import com.example.praticetokensecurity.token.repository.RefreshTokenRepository;
import com.example.praticetokensecurity.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken saveToken(String refreshToken, Long userId){
        Optional<RefreshToken> existingToken = refreshTokenRepository.findByUserId(userId);

        if(existingToken.isPresent()){
            RefreshToken token = existingToken.get();
            token.update(refreshToken, LocalDateTime.now().plusDays(7));
            refreshTokenRepository.save(token);
            return token;
        }

        RefreshToken newToken = RefreshToken.of(userId,refreshToken,LocalDateTime.now().plusDays(7));
        refreshTokenRepository.save(newToken);
        return newToken;
    }

}
