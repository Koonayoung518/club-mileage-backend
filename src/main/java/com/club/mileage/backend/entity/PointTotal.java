package com.club.mileage.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "point_total", indexes = {
        @Index(name = "idx_point_total_users", columnList = "users_id")
})
@NoArgsConstructor
public class PointTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Long total = 0L;

    @OneToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @Builder
    public PointTotal(Long total, Users users){
        this.total  = total;
        this.users = users;
    }

    public void updatePointTotal(Long total){
        this.total = total;
    }
}
