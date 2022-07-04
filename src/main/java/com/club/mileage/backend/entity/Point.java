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
@Table(name = "point")
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

    @Column(name = "point")
    private Long point;

    @Column(name = "target_id")
    private String targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Point (EventType eventType, Long point, String targetId, User user){
        this.eventType = eventType;
        this.point = point;
        this.targetId = targetId;
        this.user = user;
    }
}
