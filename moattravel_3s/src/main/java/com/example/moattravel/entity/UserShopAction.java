package com.example.moattravel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "user_shop_actions")
@Data
public class UserShopAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "shop_id")
    private Integer shopId;
    
 // click / detail / favoriteそれぞれを想定
    @Column(name = "action_type")
    private String actionType; 

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}