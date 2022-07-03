package com.club.mileage.backend.entity;

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
    private String id = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "place")
    private List<Review> reviewList = new ArrayList<>();

}
