package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;

public class ResponseReview {
    @Builder
    @Data
    public static class getMyReview{
        private String reviewId;
        private String content;
    }
}
