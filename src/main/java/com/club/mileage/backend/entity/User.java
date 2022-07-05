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
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    private String userId = UUID.randomUUID().toString();

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(mappedBy = "user")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Point> pointList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<PointHistory> pointHistoryList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private PointTotal pointTotal;

    @Builder
    public User(String nickname){
        this.nickname = nickname;
    }

    public void updatePoint(Point point, PointHistory pointHistory){
        this.pointList.add(point);
        this.pointHistoryList.add(pointHistory);
    }

    public void updatePointTotal(PointTotal pointTotal){
        this.pointTotal = pointTotal;
    }
    public void addReview(Review review){
        this.reviewList.add(review);
    }
}
