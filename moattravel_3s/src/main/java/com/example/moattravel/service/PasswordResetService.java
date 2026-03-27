package com.example.moattravel.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel.entity.PasswordResetToken;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.PasswordResetTokenRepository;


@Service
@Transactional
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    // トークンを発行する
    public String createToken(User user) {

        // 既存トークン削除（古いものは使えないようにする
        tokenRepository.deleteByUser(user);
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setUser(user);
        resetToken.setToken(token);
       //有効な時間は10分にする
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); 

        //  保存する
        tokenRepository.save(resetToken);

        return token;
    }

    
    public PasswordResetToken findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

   
    public boolean isValidToken(PasswordResetToken token) {

        if (token == null) return false;

        return token.getExpiryDate().isAfter(LocalDateTime.now());
    }

    
    public void deleteToken(PasswordResetToken token) {
        tokenRepository.delete(token);
    }
    
    //メールの送信機能
    public String createResetUrl(String token) {
        return "http://localhost:8080/reset-password?token=" + token;
    }
}