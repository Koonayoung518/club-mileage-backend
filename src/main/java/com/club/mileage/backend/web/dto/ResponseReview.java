package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResponseReview {
    @Builder
    @Data
    public static class getMyReview{
        private String reviewId;
        private String content;
    }

    @Builder
    @Data
    public static class review{
        private String type;
        private String action;
        private String reviewId;
        private String content;
        private List<String> attachedPhotoIds;
        private String placeId;
        private String userId;
    }
}
