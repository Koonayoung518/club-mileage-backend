package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Photo;
import com.club.mileage.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, String> {
    List<Photo> findByReview(Review review);
}
