package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Point;
import com.club.mileage.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query(value = "select sum(p.point) from Point p inner join p.user where p.user = :user")
    Long findByTotal(User user);

    List<Point> findByUser(User user);

    Point findByUserAndTargetId(User user, String targetId);
}
