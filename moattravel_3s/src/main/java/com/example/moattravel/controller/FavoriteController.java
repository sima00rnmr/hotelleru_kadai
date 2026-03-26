package com.example.moattravel.controller;

import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.moattravel.entity.Favorite;
import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.FavoriteRepository;
import com.example.moattravel.repository.HouseRepository;
import com.example.moattravel.repository.UserRepository;
import com.example.moattravel.service.FavoriteService;

@Controller
public class FavoriteController {

	private final FavoriteService favoriteService;
	private final FavoriteRepository favoriteRepository;
	private final HouseRepository houseRepository;
	private final UserRepository userRepository;

	public FavoriteController(FavoriteService favoriteService, FavoriteRepository favoriteRepository,
			HouseRepository houseRepository, UserRepository userRepository) {
		this.favoriteService = favoriteService;
		this.favoriteRepository = favoriteRepository;
		this.houseRepository = houseRepository;
		this.userRepository = userRepository;

	}

	@GetMapping("/favorites")
	public String listFavorites(
			@PageableDefault(size = 10) Pageable pageable,
			Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();

		User user = userRepository.findByEmail(email);

		Page<Favorite> favorites = favoriteRepository
				.findByUserOrderByCreatedAtDesc(user, pageable);
		model.addAttribute("favorites", favorites);

		return "favorites/index";
	}

	@PostMapping("/favorites/toggle/{houseId}")
	@ResponseBody
	public Map<String, Object> toggleFavoriteAjax(@PathVariable Integer houseId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		User user = userRepository.findByEmail(email);
		House house = houseRepository.getReferenceById(houseId);

		favoriteService.toggleFavorite(user, house);
		boolean isFavorite = favoriteService.isFavorite(user, house);

		Map<String, Object> result = new HashMap<>();
		result.put("houseId", houseId);
		result.put("isFavorite", isFavorite);
		return result;
	}

}