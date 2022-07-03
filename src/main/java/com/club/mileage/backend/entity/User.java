package com.club.mileage.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private String id = UUID.randomUUID().toString();

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Point> pointList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PointHistory> pointHistoryList = new ArrayList<>();
}
