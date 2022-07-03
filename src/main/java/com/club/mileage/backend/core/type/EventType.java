package com.club.mileage.backend.core.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    REVIEW("리뷰"),
    FLIGHT("항공"),
    ACCOMMODATION("숙소"),
    INVITE("친구 초대");

    private String title;
}
