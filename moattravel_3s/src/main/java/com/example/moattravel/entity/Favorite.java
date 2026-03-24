package com.example.moattravel.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//ManyToOneを使いそうなので…（ユーザーと紐付け）
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
//重複防止に使える
import jakarta.persistence.UniqueConstraint;

//ゲッターセッター自動生成
import lombok.Data;

@Entity

@Table(name = "favorites",
//重複防止するため　制御そのものはコントローラーかサービス側で行う
uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "house_id"}))
@Data
public class Favorite{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	//ユーザー ManyToOne使う
	@ManyToOne
	/*備えとして, 『nullable』　idがnullのuserやhouse作れる
	 * 状況を現段階で想像は出来なかったけど
	 * 万が一作れちゃった状況が発生したらヤバい。
	 * と理解できたので記載
	 * 学習を進めて、理解が深まった後に
	 * 無くても問題ないな…と思った場合は削除する
	 * */
	@JoinColumn(name ="user_id", nullable = false)
	private User user;
	
	//宿　ManyToOne使う
	@ManyToOne
	@JoinColumn(name ="house_id", nullable = false)
	private House house;
	
	//作成日 今回作成or削除なのでupdatedはいらないかも？
	@Column(name ="created_at")
	private LocalDateTime createdAt;
	
	@PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        
        /*今は特段入れる必要性を感じなかったけどオススメされた機能
         * ・インデックス（パフォーマンス） @Index
         * 速くなる、らしい
         * 
         * ・ メモ・タグ系（将来用）
         * 「お気に入り理由」とかに使えるらしい
         * 口コミとリンクさせたら便利そう？
         * 
         * 
         * */
    }
	
	
}