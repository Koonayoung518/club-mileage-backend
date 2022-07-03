package com.club.mileage.backend.core.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActionType {
    ADD("리뷰 생성"),
    MOD("리뷰 수정"),
    DELETE("리뷰 삭제");

    private String detail;
}
