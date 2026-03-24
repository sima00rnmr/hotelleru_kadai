package com.example.moattravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.FavoriteRepository;
import com.example.moattravel.repository.HouseRepository;
import com.example.moattravel.service.FavoriteService;

@Controller
public class FavoriteController{

	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final HouseRepository houseRepository;
	
	public FavoriteController(FavoriteService favoriteService,FavoriteRepository favoriteRepository,HouseRepository houseRepository){	
		this.favoriteService=favoriteService;
		this.favoriteRepository=favoriteRepository;
		this.houseRepository=houseRepository;
	}
	
	@GetMapping("/favorites")
	public String listFavorites(@AuthenticationPrincipal User user,
			@PageableDefault(size = 10) Pageable pageable,Model model) {
		Page<House>favorites = favoriteService.getFavorites(user, pageable);
	model.addAttribute("favorites",favorites);
	return "favorites/index";
	}
			
	
	
	@PostMapping("/favorites/toggle/{houseId}")
	public String toggleFavorite(@PathVariable Integer houseId,@AuthenticationPrincipal User user){
		House house = houseRepository.getReferenceById(houseId);
		
		favoriteService.toggleFavorite(user,house);
		
		//宿にリダイレクト（ダブル送信を禁止）
		return "redirect:/houses/" + houseId;
		
	
	}	
	
}