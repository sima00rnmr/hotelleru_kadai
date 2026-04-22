package com.example.moattravel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.moattravel.repository.UserShopActionRepository;

@Service
public class ShopRecommendationService {

    private final UserShopActionRepository userShopActionRepository;

    public ShopRecommendationService(UserShopActionRepository userShopActionRepository) {
        this.userShopActionRepository = userShopActionRepository;
    }

    public List<String> getTopCategories(Integer userId) {
        return userShopActionRepository.findTopCategoriesByUserId(userId);
    }
}