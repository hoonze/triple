package com.triple.review.db.repository;

import com.triple.review.db.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    boolean existsByPlaceId(String placeId);

    boolean existsByUserIdAndPlaceId(String userId, String placeId);
}
