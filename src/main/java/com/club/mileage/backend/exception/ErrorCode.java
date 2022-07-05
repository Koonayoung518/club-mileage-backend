package com.club.mileage.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND_USER(HttpStatus.NOT_FOUND,"USER_001","해당 유저를 찾을 수 없음."),
    NOT_FOUND_PLACE(HttpStatus.NOT_FOUND,"PLACE_001","해당 장소를 찾을 수 없음."),
    NOT_FOUND_REVIEW(HttpStatus.NOT_FOUND,"REVIEW_001","해당 리뷰를 찾을 수 없음."),
    DUPLICATED_REVIEW(HttpStatus.FORBIDDEN, "REVIEW_002", "이미 리뷰가 있음."),
    FAILED_UPDATE_POINT(HttpStatus.NOT_FOUND, "POINT_001", "포인트 적립 실패."),
    DUPLICATED_POINT(HttpStatus.NOT_FOUND, "POINT_002", "이미 포인트 적립됨.");
    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorCode(final HttpStatus httpStatus, final String code, final String message){
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
