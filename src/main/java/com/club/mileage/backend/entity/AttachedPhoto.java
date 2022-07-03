package com.club.mileage.backend.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "attached_photo")
@NoArgsConstructor
public class AttachedPhoto {
    @Id
    @Column(name = "photo_id")
    private String id = UUID.randomUUID().toString();

    @Column(name = "url")
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

}
