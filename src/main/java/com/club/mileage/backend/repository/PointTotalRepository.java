package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.PointTotal;
import com.club.mileage.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointTotalRepository extends JpaRepository<PointTotal, Long> {
    PointTotal findByUsers(Users users);
}
