package com.example.moattravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.moattravel.entity.ReservationArchive;

public interface ReservationArchiveRepository
        extends JpaRepository<ReservationArchive, Integer> {
	
}