package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Review;
import com.club.mileage.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByUsersAndPlace(Users users, Place place);
    Review findByUsersAndReviewId(Users users, String reviewId);
}
