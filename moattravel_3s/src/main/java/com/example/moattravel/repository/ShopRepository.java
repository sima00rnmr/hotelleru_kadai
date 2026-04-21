package com.example.moattravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    // 市区町村検索 今は一旦これ。　最終的には緯度経度から２拠点の距離を出せるようにする
    List<Shop> findByAddressContaining(String city);
}