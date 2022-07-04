package com.club.mileage.backend.entity;

import com.club.mileage.backend.core.type.EventType;
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
@Table(name = "review")
@NoArgsConstructor
public class Review {
    @Id
    @Column(name = "review_id")
    private String id = UUID.randomUUID().toString();

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "attached_photo")
    private List<AttachedPhoto> photoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "review_type")
    @Enumerated(value = EnumType.STRING)
    private ReviewType reviewType;

    @Builder
    public Review(String content, Place place ,User user, ReviewType reviewType){
        this.content = content;
        this.user = user;
        this.place = place;
        this.reviewType = reviewType;
    }
}
