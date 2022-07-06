package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Point;
import com.club.mileage.backend.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PointRepository extends JpaRepository<Point, Long> {
    @Query(value = "select sum(p.point) from Point p inner join p.users where p.users = :users")
    Long findByTotal(Users users);

    List<Point> findByUsers(Users users);

    Point findByUsersAndTargetId(Users users, String targetId);
}
