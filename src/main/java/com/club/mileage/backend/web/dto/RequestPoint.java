package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class RequestPoint {
    @Builder
    @Data
    public static class register{
        private String type;
        private String action;
        private String reviewId;
        private String content;
        private List<String> attachedPhotoIds;
        private String userId;
        private String placeId;
    }
}
