package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;


public class RequestReview {
    @Builder
    @Data
    public static class register{
        private String content;
        private String placeId;
    }
}