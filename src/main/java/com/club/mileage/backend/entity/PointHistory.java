package com.club.mileage.backend.entity;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Table(name = "point_history")
@NoArgsConstructor
public class PointHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "event_type")
    @Enumerated(value = EnumType.STRING)
    private EventType eventType;

    @Column(name = "action_type")
    @Enumerated(value = EnumType.STRING)
    private ActionType actionType;

    @Column(name = "point")
    private Long point;

    @Column(name = "target_id")
    private String targetId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PointHistory(EventType eventType, ActionType actionType, Long point, String targetId, User user){
        this.eventType = eventType;
        this.actionType = actionType;
        this.point = point;
        this.targetId = targetId;
        this.user = user;
    }
}
