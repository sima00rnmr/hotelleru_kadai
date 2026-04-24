package com.example.moattravel.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.moattravel.entity.Shop;
import com.example.moattravel.repository.ShopRepository;
import com.example.moattravel.repository.UserShopActionRepository;

@Service
public class ShopService {

	private final ShopRepository shopRepository;
	private final UserShopActionRepository userShopActionRepository;

	public ShopService(ShopRepository shopRepository, UserShopActionRepository userShopActionRepository) {
		this.shopRepository = shopRepository;
		this.userShopActionRepository = userShopActionRepository;
	}

	/* 市区町村で同じ所を抽出して提示する
	 * → うち4件を好みのカテゴリーから
	 * → 残りはその他ランダムで補完（userの好み変化の情報収集用）
	 */
	public List<Shop> getRecommendedShops(String address, Integer userId) {
		String city = extractCity(address);

		List<Shop> nearbyShops = shopRepository.findByAddressContaining(city);
		List<String> topCategories = getTopCategories(userId);

		List<Shop> preferred = new ArrayList<>();
		List<Shop> others = new ArrayList<>();

		for (Shop shop : nearbyShops) {
			if (topCategories.contains(shop.getCategory())) {
				preferred.add(shop);
			} else {
				others.add(shop);
			}
		}

		Collections.shuffle(preferred);
		Collections.shuffle(others);

		List<Shop> result = new ArrayList<>();

		int preferredCount = Math.min(4, preferred.size());
		result.addAll(preferred.subList(0, preferredCount));

		int remain = 6 - result.size();
		int otherCount = Math.min(remain, others.size());
		result.addAll(others.subList(0, otherCount));

		return result;
	}

	/* 市区町村かつジャンルで抽出する場合
	 * カテゴリタグをクリックしたときの遷移先の表示
	 */
	public List<Shop> findByAddressAndCategory(String address, String category) {
		String city = extractCity(address);
		return shopRepository.findByAddressContainingAndCategory(city, category);
	}

	// カテゴリーのルール
	public List<String> getTopCategories(Integer userId) {
		return userShopActionRepository.findTopCategoriesByUserId(userId);
	}

	public List<Shop> findByCategory(String category) {
		return shopRepository.findByCategory(category);
	}

	private String extractCity(String address) {
		if (address == null || address.isEmpty()) {
			return "";
		}

		if (address.contains("区")) {
			return address.substring(0, address.indexOf("区") + 1);
		}

		if (address.contains("市")) {
			return address.substring(0, address.indexOf("市") + 1);
		}

		if (address.contains("町")) {
			return address.substring(0, address.indexOf("町") + 1);
		}

		if (address.contains("村")) {
			return address.substring(0, address.indexOf("村") + 1);
		}

		return address;
	}
	
	
}