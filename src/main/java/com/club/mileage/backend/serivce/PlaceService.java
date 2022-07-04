package com.club.mileage.backend.serivce;

import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public Place mockPlace(){
        //임의의 장소
        Place place = Place.builder()
                .placeName("수목원")
                .build();
        place= placeRepository.save(place);
        return place;
    }
}
