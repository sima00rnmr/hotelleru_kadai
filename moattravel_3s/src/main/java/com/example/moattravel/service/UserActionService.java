package com.example.moattravel.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.moattravel.entity.Shop;
import com.example.moattravel.entity.User;
import com.example.moattravel.entity.UserShopAction;
import com.example.moattravel.repository.UserShopActionRepository;

@Service
public class UserActionService {

	private final UserShopActionRepository userShopActionRepository;

	public UserActionService(UserShopActionRepository userShopActionRepository) {
		this.userShopActionRepository = userShopActionRepository;
	}

	/*行動履歴の保存を行っている　
	 * actionType :　ここに詳細ページへ遷移した、マップリンクをクリックした等の行動履歴が入る
	 * ↑その後、再表示する際にはこのテーブル内に入っているTypeをそれぞれ数値化して結果に反映させる
	 * 
	 */
	public void saveAction(User user, Shop shop, String actionType) {
		UserShopAction action = new UserShopAction();
		action.setUser(user);
		action.setShop(shop);
		action.setActionType(actionType); // "CLICK" とか
		action.setCreatedAt(LocalDateTime.now());

		userShopActionRepository.save(action);
	}
}