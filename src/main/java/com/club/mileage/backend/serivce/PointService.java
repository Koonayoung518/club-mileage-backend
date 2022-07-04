package com.club.mileage.backend.serivce;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.*;
import com.club.mileage.backend.exception.Errors.NotFoundReviewException;
import com.club.mileage.backend.exception.Errors.NotFoundUserException;
import com.club.mileage.backend.repository.*;
import com.club.mileage.backend.web.dto.RequestPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PointService {
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public void addReviewPoint(RequestPoint.register requestDto){

        User user = userRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(()-> new NotFoundReviewException());

        Long mileage = 0L;
        if(review.getContent().length()>0) // 1자 이상 텍스트 작성 시 1점
            mileage++;
        if(!Optional.ofNullable(requestDto.getAttachedPhotoIds()).isEmpty()) //1장 이상 사진 첨부 시 1점
            mileage++;
        if(review.getReviewType().equals(ReviewType.FIRST))// 특정 장소에 첫 리뷰 작성 시 1점
            mileage++;

        //포인트 등록
        Point point = Point.builder()
                .eventType(EventType.REVIEW)
                .point(mileage)
                .targetId(review.getReviewId())
                .user(user)
                .build();
        pointRepository.save(point);

        //포인트 히스토리에 등록
        PointHistory history = PointHistory.builder()
                .eventType(EventType.REVIEW)
                .actionType(ActionType.ADD)
                .point(mileage)
                .details(review.getReviewId())
                .user(user)
                .build();
        pointHistoryRepository.save(history);

        //포인트 총합 업데이트
        Long total = pointRepository.findByTotal(user);
        PointTotal pointTotal = PointTotal.builder()
                .total(total)
                .build();
    }

    @Transactional
    public void modReviewPoint(RequestPoint.register requestDto){

    }

    @Transactional
    public void deleteReviewPoint(RequestPoint.register requestDto){

    }
}
