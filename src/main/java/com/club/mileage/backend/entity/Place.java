package com.club.mileage.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "place")
@NoArgsConstructor
public class Place {
    @Id
    @Column(name = "place_id")
    private String placeId = UUID.randomUUID().toString();

    @Column(name = "place_name")
    private String placeName;

    @OneToMany(mappedBy = "place")
    private List<Review> reviewList = new ArrayList<>();

    @Builder
    public Place (String placeName){
        this.placeName = placeName;
    }

    public void addReview(Review review){
        this.reviewList.add(review);
    }
}
