package com.club.mileage.backend.repository;

import com.club.mileage.backend.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {
}
