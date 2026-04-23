package com.example.moattravel.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.example.moattravel.entity.Shop;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.ShopRepository;
import com.example.moattravel.security.UserDetailsImpl;
import com.example.moattravel.service.ShopService;
import com.example.moattravel.service.UserActionService;

@Controller
public class ShopController {

	private final ShopRepository shopRepository;
	private final UserActionService userActionService;
	private final ShopService shopService;

	public ShopController(ShopRepository shopRepository, UserActionService userActionService, ShopService shopService) {
		this.shopRepository = shopRepository;
		this.userActionService = userActionService;
		this.shopService = shopService;
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

	//カテゴリーボタンを押した時の挙動
	@GetMapping("/shops/category")
	public String showShopsByCategory(@RequestParam String category,
			@RequestParam String address,
			Model model) {

		List<Shop> shopList = shopService.findByAddressAndCategory(address, category);

		model.addAttribute("shopList", shopList);
		model.addAttribute("category", category);

		return "shops/category_list";
	}
	
	//詳細ページからGoogleマップへのリンクをクリックした場合の動き
	@GetMapping("/shops/{id}/map")
	public RedirectView openMap(@PathVariable Integer id,
	        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {

	    Shop shop = shopRepository.findById(id).orElseThrow();

	    if (userDetailsImpl != null) {
	        User user = userDetailsImpl.getUser();
	        userActionService.saveAction(user, shop, "map");
	    }

	    String query = shop.getName() + " " + shop.getAddress();
	    String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
	    String url = "https://www.google.com/maps/search/?api=1&query=" + encodedQuery;

	    return new RedirectView(url);
	}

}