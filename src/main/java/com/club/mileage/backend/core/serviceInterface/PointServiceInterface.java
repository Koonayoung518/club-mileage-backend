package com.club.mileage.backend.core.serviceInterface;

import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponsePoint;

public interface PointServiceInterface {
    void addReviewPoint(RequestPoint.register requestDto);
    void modReviewPoint(RequestPoint.register requestDto);
    void deleteReviewPoint(RequestPoint.register requestDto);
    ResponsePoint.getPointHistory getPointHistory(String userId);
}
