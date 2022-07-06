package com.club.mileage.backend.entity;

import com.club.mileage.backend.core.type.ReviewType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Table(name = "review", indexes = {
        @Index(name = "idx_review_users_place", columnList = "users_id, place_id"),
        @Index(name = "idx_review_users_review_id", columnList = "users_id, review_id")
})
@NoArgsConstructor
public class Review {
    @Id
    @Column(name = "review_id")
    private String reviewId = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Photo> photoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Column(name = "review_type")
    @Enumerated(value = EnumType.STRING)
    private ReviewType reviewType;

    @Builder
    public Review(String content, Place place , Users users, ReviewType reviewType){
        this.content = content;
        this.users = users;
        this.place = place;
        this.reviewType = reviewType;
    }

    public void addPhoto(Photo photo){
        this.photoList.add(photo);
    }
    public void updateReview(String content){
        this.content = content;
    }
}
