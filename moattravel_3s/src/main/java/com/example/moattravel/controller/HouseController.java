package com.example.moattravel.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;
import com.example.moattravel.form.ReservationInputForm;
import com.example.moattravel.repository.HouseRepository;
import com.example.moattravel.service.FavoriteService;

@Controller
@RequestMapping("/houses")
public class HouseController{
	private final HouseRepository houseRepository;
	private final FavoriteService favoriteService;
	
	public HouseController(HouseRepository houseRepository,FavoriteService favoriteService) {
		this.houseRepository = houseRepository;
		this.favoriteService = favoriteService;
	}
	
	@GetMapping
	public String index(@RequestParam(name = "keyword", required = false) String keyword,
			@RequestParam(name ="area",required =false)String area,
			 @RequestParam(name = "price", required = false) Integer price,  
			 @RequestParam(name = "order", required = false) String order,  
			 @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
			 Model model) {
	Page<House>housePage;
	
	if (keyword != null && !keyword.isEmpty()) {

	    housePage = houseRepository.findByNameLikeOrAddressLike("%" + keyword + "%", "%" + keyword + "%", pageable);

	    if (order != null && order.equals("priceAsc")) {
	        housePage = houseRepository.findByNameLikeOrAddressLikeOrderByPriceAsc("%" + keyword + "%", "%" + keyword + "%", pageable);
	    } else {
	        housePage = houseRepository.findByNameLikeOrAddressLikeOrderByCreatedAtDesc("%" + keyword + "%", "%" + keyword + "%", pageable);
	    }

	} else if (area != null && !area.isEmpty()) {

	    housePage = houseRepository.findByAddressLike("%" + area + "%", pageable);

	    if (order != null && order.equals("priceAsc")) {
	        housePage = houseRepository.findByAddressLikeOrderByPriceAsc("%" + area + "%", pageable);
	    } else {
	        housePage = houseRepository.findByAddressLikeOrderByCreatedAtDesc("%" + area + "%", pageable);
	    }

	} else if (price != null) {

	    housePage = houseRepository.findByPriceLessThanEqual(price, pageable);

	    if (order != null && order.equals("priceAsc")) {
	        housePage = houseRepository.findByPriceLessThanEqualOrderByPriceAsc(price, pageable);
	    } else {
	        housePage = houseRepository.findByPriceLessThanEqualOrderByCreatedAtDesc(price, pageable);
	    }

	} else {

	    housePage = houseRepository.findAll(pageable);

	    if (order != null && order.equals("priceAsc")) {
	        housePage = houseRepository.findAllByOrderByPriceAsc(pageable);
	    } else {
	        housePage = houseRepository.findAllByOrderByCreatedAtDesc(pageable);
	    }
	
	}
	model.addAttribute("housePage", housePage);
	model.addAttribute("keyword",keyword );
	model.addAttribute("area",area );
	model.addAttribute("price",price );
	model.addAttribute("order",order );
	
	return "houses/index";
	
	}
	@GetMapping("/{id}")
	public String show(@PathVariable(name ="id")Integer id,
			@AuthenticationPrincipal User user,  // ここでログインユーザー取得
			Model model) {
		House house =houseRepository.getReferenceById(id);
		
		//お気に入り登録のフラグ
		if(user != null) {
			boolean isFavorite = favoriteService.isFavorite(user,house);
		house.setFavorite(isFavorite);
		}else {
			house.setFavorite(false);
		}
		
		model.addAttribute("house",house);
		model.addAttribute("reservationInputForm",new ReservationInputForm());
		
		return "houses/show";
	}
	
}