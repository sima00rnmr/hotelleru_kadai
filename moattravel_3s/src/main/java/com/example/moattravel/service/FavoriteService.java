package com.example.moattravel.service;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel.entity.Favorite;
import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;
import com.example.moattravel.repository.FavoriteRepository;

@Service
@Transactional
public class FavoriteService{
	private final FavoriteRepository favoriteRepository;
	
	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}
	//トグル登録
	public boolean toggleFavorite(User user, House house) {
		Optional<Favorite>existing =favoriteRepository.findByUserAndHouse(user,house);
		//削除時の動き
		if(existing.isPresent()) {
			favoriteRepository.delete(existing.get());
			return false;
		}else {
			//登録時の動き
			Favorite fav = new Favorite();
			fav.setUser(user);
			fav.setHouse(house);
			favoriteRepository.save(fav);
			return true;
			
		}
	}
	//ログインユーザーのお気に入りリストを取得する
	public Page<Favorite>getFavorite(User user,Pageable pageable){
		return favoriteRepository.findByUserOrderByCreatedAtDesc(user,pageable);
	}
	
	
	
}
