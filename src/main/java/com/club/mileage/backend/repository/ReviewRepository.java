package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
