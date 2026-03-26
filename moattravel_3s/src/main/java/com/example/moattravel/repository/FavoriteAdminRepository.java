package com.example.moattravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.moattravel.entity.Favorite;

public interface FavoriteAdminRepository extends JpaRepository<Favorite, Integer> {

    @Query("""
        SELECT f.house.id, COUNT(f)
        FROM Favorite f
        GROUP BY f.house.id
    """)
    List<Object[]> countFavoritesGroupByHouse();
    @Query("""
    	    SELECT h, COUNT(f)
    	    FROM House h
    	    LEFT JOIN Favorite f ON h.id = f.house.id
    	    GROUP BY h
    	    ORDER BY COUNT(f) DESC
    	""")
    	Page<Object[]> findAllOrderByFavoriteCountDesc(Pageable pageable);

}