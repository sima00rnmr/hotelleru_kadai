package com.example.moattravel.service;

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
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;

	public FavoriteService(FavoriteRepository favoriteRepository) {
		this.favoriteRepository = favoriteRepository;
	}

	//お気に入り登録済みかの判断
	public boolean isFavorite(User user, House house) {
		return favoriteRepository.existsByUserAndHouse(user, house);

	}

	// トグル（登録／削除）
	@Transactional
	public void toggleFavorite(User user, House house) {
		favoriteRepository.findByUserAndHouse(user, house)
				.ifPresentOrElse(
						favoriteRepository::delete,
						() -> {
							Favorite favorite = new Favorite();
							favorite.setUser(user);
							favorite.setHouse(house);
							favoriteRepository.save(favorite);
						});
	}

	// ユーザーのお気に入り一覧取得
	public Page<House> getFavorites(User user, Pageable pageable) {
		// Page<Favorite> を取得
		Page<Favorite> favoritesPage = favoriteRepository.findByUserWithHouse(user, pageable);

		// Page<Favorite> を Page<House> に変換
		return favoritesPage.map(Favorite::getHouse);
	}
}