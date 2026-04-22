package com.example.moattravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.moattravel.entity.UserShopAction;

public interface UserShopActionRepository extends JpaRepository<UserShopAction, Integer> {

    List<UserShopAction> findByUserId(Integer userId);

    //userのactionに対してお気に入り、の順序を並び変えるロジック
    /*そのユーザーが興味あるものと判断する材料
     * ・詳細ページへのリンクをクリックした→'detail' THEN 2
     * ・詳細ページ内の地図へのリンクをクリックした→'map' THEN 3
     * ・詳細ページ内のお気に入りボタンをした→将来的に実装（以下が複雑なので）
     * 宿の予約データと紐付けて、予約画面で確認できるようにする
     * 宿泊日の1カ月後にお気に入りした内容は削除（actionの数値だけ残す）
     * 
     *ユーザーのアクションをユーザーデータとショップ側のデータで紐付けを行う
     *
     * */
    @Query(value = """
        SELECT s.category
        FROM user_shop_actions usa
        JOIN shops s ON usa.shop_id = s.id
        WHERE usa.user_id = :userId
          AND usa.action_type IN ('detail', 'map')
        GROUP BY s.category
        ORDER BY SUM(
            CASE
                WHEN usa.action_type = 'map' THEN 3
                WHEN usa.action_type = 'detail' THEN 2
                ELSE 0
            END
        ) DESC
         LIMIT 3
        """, nativeQuery = true)
    List<String> findTopCategoriesByUserId(Integer userId);
}