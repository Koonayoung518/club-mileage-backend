package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;


public class RequestReview {
    @Builder
    @Data
    public static class register{
        private String userId;
        private String content;
        private String placeId;
    }
    @Builder
    @Data
    public static class update{
        private String userId;
        private String content;
        private String placeId;
    }
}
