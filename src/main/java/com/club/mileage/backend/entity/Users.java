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
public class Users {
    @Id
    @Column(name = "users_id")
    private String usersId = UUID.randomUUID().toString();

    @Column(name = "nickname")
    private String nickname;

    @OneToMany(mappedBy = "users")
    private List<Review> reviewList = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<Point> pointList = new ArrayList<>();

    @OneToMany(mappedBy = "users")
    private List<PointHistory> pointHistoryList = new ArrayList<>();

    @OneToOne(mappedBy = "users")
    private PointTotal pointTotal;

    @Builder
    public Users(String nickname){
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
