package com.example.moattravel.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.Reservation;
import com.example.moattravel.entity.User;



public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

	public Page<Reservation>
	findByUserOrderByCreatedAtDesc(User user,Pageable pageable);
	
	
	//バッチ処理にて追加　古い予約データを呼び出す
	List<Reservation> findByCheckinDateBefore(LocalDate date);
	
	
	 List<Reservation> findByUserId(Integer userId);
}
