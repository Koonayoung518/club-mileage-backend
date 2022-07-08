package com.club.mileage.backend.core.serviceInterface;

import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponsePoint;

public interface PointServiceInterface {
    ResponsePoint.updatePoint addReviewPoint(RequestPoint.register requestDto);
    ResponsePoint.updatePoint modReviewPoint(RequestPoint.register requestDto);
    ResponsePoint.updatePoint deleteReviewPoint(RequestPoint.register requestDto);
    ResponsePoint.getPointHistory getPointHistory(String userId);
    Long getPointTotal(String userId);
}
