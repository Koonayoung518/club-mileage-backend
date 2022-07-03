package com.club.mileage.backend.entity;

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
    @JoinColumn(name = "user_id")
    private User user;

}
