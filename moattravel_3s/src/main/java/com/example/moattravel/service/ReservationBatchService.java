package com.example.moattravel.service;


import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.moattravel.entity.Reservation;
import com.example.moattravel.entity.ReservationArchive;
import com.example.moattravel.repository.ReservationArchiveRepository;
import com.example.moattravel.repository.ReservationRepository; 

@Service
public class ReservationBatchService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationArchiveRepository archiveRepository;

    @Transactional
    public void archiveOldReservations() {

        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        //チェックイン日から1年以上経過している古い予約を抽出する
        List<Reservation> oldReservations =
            reservationRepository.findByCheckinDateBefore(oneYearAgo);

        // ↑の抽出件数が0件の場合は以下の作業は実施しない
        if (oldReservations.isEmpty()) {
            return;
        }

        // archiveへ以降する
        List<ReservationArchive> archives = oldReservations.stream()
            .map(this::convertToArchive)
            .toList();

        // アーカイブデータを保存する
        archiveRepository.saveAll(archives);

        // Reservationテーブルから削除する
        reservationRepository.deleteAll(oldReservations);
    }

    // 変換処理
    private ReservationArchive convertToArchive(Reservation r) {

        ReservationArchive archive = new ReservationArchive();

        archive.setId(r.getId()); // ← 超重要（ID引き継ぎ）
        archive.setHouse(r.getHouse());
        archive.setUser(r.getUser());
        archive.setCheckinDate(r.getCheckinDate());
        archive.setCheckoutDate(r.getCheckoutDate());
        archive.setNumberOfPeople(r.getNumberOfPeople());
        archive.setAmount(r.getAmount());
        archive.setCreatedAt(r.getCreatedAt());
        archive.setUpdatedAt(r.getUpdatedAt());

        return archive;
    }
}