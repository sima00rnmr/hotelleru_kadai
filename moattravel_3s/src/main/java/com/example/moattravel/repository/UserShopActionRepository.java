package com.example.moattravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.UserShopAction;

public interface UserShopActionRepository extends JpaRepository<UserShopAction, Integer> {

    List<UserShopAction> findByUserId(Integer userId);
}