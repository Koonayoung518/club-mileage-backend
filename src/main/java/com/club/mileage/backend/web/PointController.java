package com.club.mileage.backend.web;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.exception.Errors.FailedUpdatePointException;
import com.club.mileage.backend.exception.Errors.NotFoundPlaceException;
import com.club.mileage.backend.serivce.PointService;
import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/events")
    public ResponseEntity<ResponseMessage> updatePoint(@RequestBody RequestPoint.register requestDto) {
        switch (ActionType.valueOf(requestDto.getAction())) {
            case ADD:
                pointService.addReviewPoint(requestDto);
                break;
            case MOD:
                pointService.modReviewPoint(requestDto);
                break;
            case DELETE:
                pointService.deleteReviewPoint(requestDto);
                break;
            default:
                throw new FailedUpdatePointException();
        }
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("포인트 업데이트 성공")
                .build(), HttpStatus.OK);
    }
    }