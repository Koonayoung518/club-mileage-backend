package com.club.mileage.backend.web;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.exception.Errors.FailedUpdatePointException;
import com.club.mileage.backend.exception.Errors.NotFoundPlaceException;
import com.club.mileage.backend.serivce.PointService;
import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponseMessage;
import com.club.mileage.backend.web.dto.ResponsePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @PostMapping("/events")
    public ResponseEntity<ResponseMessage> updatePoint(@RequestBody RequestPoint.register requestDto) {
        ResponsePoint.updatePoint response = null;
        switch (ActionType.valueOf(requestDto.getAction())) {
            case ADD:
                response = pointService.addReviewPoint(requestDto);
                break;
            case MOD:
                response = pointService.modReviewPoint(requestDto);
                break;
            case DELETE:
                response = pointService.deleteReviewPoint(requestDto);
                break;
            default:
                throw new FailedUpdatePointException();
        }
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("포인트 적립 성공")
                .list(response)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/events")
    public ResponseEntity<ResponseMessage> getPointHistory(@RequestParam String userId){

        ResponsePoint.getPointHistory response = pointService.getPointHistory(userId);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("포인트 조회 성공")
                .list(response)
                .build(), HttpStatus.OK);
    }

    @GetMapping("/point/total")
    public ResponseEntity<ResponseMessage> getPointTotal(@RequestParam String userId){

        Long response = pointService.getPointTotal(userId);
        Map<String, Long> map = new HashMap<>();
        map.put("pointTotal", response);
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("포인트 총합 조회 성공")
                .list(map)
                .build(), HttpStatus.OK);
    }
    }
