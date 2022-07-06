package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.PointHistory;
import com.club.mileage.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    List<PointHistory> findByUsers(Users users);
}
