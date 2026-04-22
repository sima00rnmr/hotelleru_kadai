package com.example.moattravel.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.moattravel.entity.Shop;
import com.example.moattravel.repository.ShopRepository;
import com.example.moattravel.repository.UserShopActionRepository;

@Service
public class ShopService {

    private final ShopRepository shopRepository;
    private final UserShopActionRepository userShopActionRepository;

    public ShopService(ShopRepository shopRepository,UserShopActionRepository userShopActionRepository) {
        this.shopRepository = shopRepository;
        this.userShopActionRepository = userShopActionRepository;
    }
    //市区町村で同じ所を抽出して提示する
    public List<Shop> getRecommendedShops(String address) {
        String city = extractCity(address);
        return shopRepository.findByAddressContaining(city);
    }
    public List<Shop> findByAddressAndCategory(String address, String category) {
        return shopRepository.findByAddressContainingAndCategory(address, category);
    }
    
    //カテゴリーのルール
    public List<String> getTopCategories(Integer userId) {
        return userShopActionRepository.findTopCategoriesByUserId(userId);
    }

    private String extractCity(String address) {
        if (address == null || address.isEmpty()) {
            return "";
        }

        if (address.contains("市")) {
            return address.substring(0, address.indexOf("市") + 1);
        }

        if (address.contains("区")) {
            return address.substring(0, address.indexOf("区") + 1);
        }

        if (address.contains("町")) {
            return address.substring(0, address.indexOf("町") + 1);
        }

        if (address.contains("村")) {
            return address.substring(0, address.indexOf("村") + 1);
        }

        return address;
    }
}