package com.club.mileage.backend.entity;

import com.club.mileage.backend.core.type.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "point", indexes = {
        @Index(name = "idx_point_users", columnList = "users_id"),
        @Index(name = "idx_point_users_target_id",columnList = "users_id, target_id")
}
)
@NoArgsConstructor
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "event_type")
    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

    @Column(name = "point_score")
    private Long pointScore;

    @Column(name = "target_id")
    private String targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public Point (EventType eventType, Long pointScore, String targetId, Users users){
        this.eventType = eventType;
        this.pointScore = pointScore;
        this.targetId = targetId;
        this.users = users;
    }
    public void updatePoint(Long pointScore){
        this.pointScore = pointScore;
    }
}
