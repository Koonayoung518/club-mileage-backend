package com.club.mileage.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "photo")
@Entity
@Getter
@NoArgsConstructor
public class Photo {
    @Id
    @Column(name = "photo_id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Builder
    public Photo(String url, Review review){
        this.url = url;
        this.review = review;
    }
}
