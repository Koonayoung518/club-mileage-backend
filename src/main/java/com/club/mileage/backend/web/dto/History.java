package com.club.mileage.backend.web.dto;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Date createdAt;
    private String targetId;
    private EventType eventType;
    private ActionType actionType;
    private Long point;
}
