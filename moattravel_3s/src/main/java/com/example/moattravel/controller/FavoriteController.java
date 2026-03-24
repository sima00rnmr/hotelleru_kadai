package com.example.moattravel.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;
import com.example.moattravel.service.FavoriteService;

@Controller
public class FavoriteController{

	private final FavoriteService favoriteService;
	
	public FavoriteController(FavoriteService favoriteService){	
		this.favoriteService=favoriteService;
	}
	
	@PostMapping("/favorites/toggle/{houseId}")
	public String toggleFavorite(@PathVariable Integer houseId,@AuthenticationPrincipal User user){
		House house =new House();
		house.setId(houseId);
		
		favoriteService.toggleFavorite(user,house);
		
		//宿にリダイレクト（ダブル送信を禁止）
		return "redirect:/houses/" + houseId;
		
	
	}
	
	
	
}