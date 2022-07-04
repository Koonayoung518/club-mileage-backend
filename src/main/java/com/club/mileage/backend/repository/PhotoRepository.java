package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, String> {
}
