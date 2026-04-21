package com.example.moattravel.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import lombok.Data;

@Entity
@Table(name = "shops")
@Data
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    //カテゴリー
    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    
    //緯度経度の取得（ここから宿の位置情報で２拠点の距離を測る）
    @Column(name = "lat")
    private Double lat;

    @Column(name = "lng")
    private Double lng;

    
    @Column(name = "rating")
    private Double rating;
    
    @Column(name = "image_url")
    private String imageUrl;

    @Transient
    private Double score; // レコメンド用
}