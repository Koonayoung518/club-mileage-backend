package com.club.mileage.backend.core.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReviewType {
    FIRST("첫 리뷰임"),
    NOT_FIRST("첫 리뷰 아님");

    private String details;
}
