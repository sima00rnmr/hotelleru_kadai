package com.example.moattravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.PasswordResetToken;
import com.example.moattravel.entity.User;

public interface PasswordResetTokenRepository extends  JpaRepository<PasswordResetToken, Integer>{
	 PasswordResetToken findByToken(String token);
	 
	 
	 //同じユーザーの古いトークンは削除する
	 void deleteByUser(User user);
	 

}
