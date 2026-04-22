package com.example.moattravel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.Shop;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    /* 市区町村検索 今は一旦これ。
     * 住所情報から
     * 同一の市区町村が記載されていたら表示する（簡易版）
     * 
     * この方法だと
     * ・市区町村は跨いでいるけど、実際の距離は近い宿と店の問題
     * ・逆に同じ市区町村だけど滅茶苦茶遠い場合　
     * この方法は不適であるといえる
     * 
     *〇将来的な実装
     *・緯度経度から２拠点の距離を出せるようにする
     *　→実際に歩いた時の相違を考慮していない
     *
     * ・実際は徒歩どれくらい…みたいな方が現実的。
     * どうやったらいいのか、検討中
     * Googleplaceで出来なくはないが、API問題
     * 都度店舗-宿間で情報を取得しておくのも馬鹿らしい。
     * user次第ではそのジャンルが表示すらされない可能性
     * →無駄になるデータにAPIの枠まで使ってすることではない
     * 
     */
    List<Shop> findByAddressContaining(String city);
}