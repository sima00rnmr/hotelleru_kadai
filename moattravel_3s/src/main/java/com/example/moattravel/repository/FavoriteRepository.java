package com.example.moattravel.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.Favorite;
import com.example.moattravel.entity.House;
import com.example.moattravel.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
	//誰のお気に入り化を条件に探す　ログインユーザーの物かどうか判別
	public Page<Favorite>findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
  /*Optional<T> は 「値があるかもしれないし、ないかもしれない」
   * ことを表すラッパークラス
   * 
   * そもそも、ラッパークラスとは…
   * プリミティブ型→ラッパークラス　のイメージ
   * 	int→Integer
   * int は単なる数値（メモリに直接値が入るだけ
   * Integer はオブジェクト（null やメソッドを持てる
   * 
   * null が扱えるってことは…
   *    int i = null; はエラーになるけど、Integer i = null;は使える！
   *   
   * つまり…プリミティブ型をオブジェクトとして扱えるようにするクラスのこと
   * 
   * 
   * Optionalを使うと…nullチェックをしなくていい
   *＞コードが読みやすくなる 
   * 
   * */
	
	//ユーザーと宿の紐付け（誰がどこにお気に入りをしているのか）
	public Optional<Favorite> findByUserAndHouse(User user, House house);
}