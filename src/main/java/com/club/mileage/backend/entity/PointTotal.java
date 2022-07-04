package com.club.mileage.backend.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "point_total")
@NoArgsConstructor
public class PointTotal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "total")
    private Long total = 0L;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PointTotal(Long total, User user){
        this.total  = total;
        this.user =  user;
    }

    public void updatePointTotal(Long total){
        this.total = total;
    }
}
