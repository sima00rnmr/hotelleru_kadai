package com.example.moattravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.moattravel.entity.Shop;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.ShopRepository;
import com.example.moattravel.security.UserDetailsImpl;
import com.example.moattravel.service.UserActionService;

@Controller
public class ShopController {

    private final ShopRepository shopRepository;
    private final UserActionService userActionService;

    public ShopController(ShopRepository shopRepository, UserActionService userActionService) {
        this.shopRepository = shopRepository;
        this.userActionService = userActionService;
    }

    @GetMapping("/shops/{id}")
    public String show(@PathVariable Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            Model model) {

        // shop情報の取得
        Shop shop = shopRepository.findById(id).orElseThrow();

        // ログインユーザーの取得
        User user = userDetailsImpl.getUser();

        // userアクションを渡す
        userActionService.saveAction(user, shop, "detail");

        // 画面に渡す
        model.addAttribute("shop", shop);

        return "shops/show";
    }
}