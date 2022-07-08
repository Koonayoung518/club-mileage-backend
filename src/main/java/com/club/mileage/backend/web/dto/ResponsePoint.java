package com.club.mileage.backend.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResponsePoint {
    @Builder
    @Data
    public static class updatePoint{
        private String action;
        private Long point;
    }
    @Builder
    @Data
    public static class getPointHistory{
        private Long pointTotal;
        private List<History> historyList;
    }
}
