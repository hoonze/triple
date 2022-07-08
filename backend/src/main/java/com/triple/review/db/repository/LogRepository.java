package com.triple.review.db.repository;

import com.triple.review.db.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LogRepository extends JpaRepository<Log, Long> {
    @Query(value = "SELECT SUM(l.value) FROM Log l WHERE l.userId = :userId")
    Optional<Integer> selectPointByUser(@Param("userId") String userId);
}
