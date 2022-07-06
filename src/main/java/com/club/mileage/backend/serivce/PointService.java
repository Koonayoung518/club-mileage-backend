package com.club.mileage.backend.serivce;

import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.*;
import com.club.mileage.backend.exception.Errors.DuplicatedPointException;
import com.club.mileage.backend.exception.Errors.FailedUpdatePointException;
import com.club.mileage.backend.exception.Errors.NotFoundReviewException;
import com.club.mileage.backend.exception.Errors.NotFoundUserException;
import com.club.mileage.backend.repository.*;
import com.club.mileage.backend.web.dto.History;
import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponsePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class PointService {
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;
    private final PointRepository pointRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PointTotalRepository pointTotalRepository;

    @Transactional
    public void addReviewPoint(RequestPoint.register requestDto){
        Users users = usersRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(()-> new NotFoundReviewException());

        Long mileage = 0L;
        if(!Optional.ofNullable(review.getContent()).isEmpty()) // 1자 이상 텍스트 작성 시 1점
            mileage++;
        if(!Optional.ofNullable(requestDto.getAttachedPhotoIds()).isEmpty()) //1장 이상 사진 첨부 시 1점
            mileage++;
        if(review.getReviewType().equals(ReviewType.FIRST))// 특정 장소에 첫 리뷰 작성 시 1점
            mileage++;

        Point point = pointRepository.findByUsersAndTargetId(users, requestDto.getReviewId());
        if(point != null){//이미 적립한 포인트가 있을 경우
            throw new DuplicatedPointException();
        }
        //포인트 등록
           point = Point.builder()
                    .eventType(EventType.REVIEW)
                    .point(mileage)
                    .targetId(review.getReviewId())
                    .users(users)
                    .build();
            pointRepository.save(point);

        //포인트 업데이트
        updatePoint(requestDto.getType(),requestDto.getAction(), mileage, requestDto.getReviewId(), users);
    }

    @Transactional
    public void modReviewPoint(RequestPoint.register requestDto){
        Users users = usersRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Review review = reviewRepository.findById(requestDto.getReviewId()).orElseThrow(()-> new NotFoundReviewException());

        Long mileage = 0L;
        if(!Optional.ofNullable(review.getContent()).isEmpty()) // 1자 이상 텍스트 작성 시 1점
            mileage++;
        if(!Optional.ofNullable(requestDto.getAttachedPhotoIds()).isEmpty()) //1장 이상 사진 첨부 시 1점
            mileage++;
        if(review.getReviewType().equals(ReviewType.FIRST))// 특정 장소에 첫 리뷰 작성 시 1점
            mileage++;

        Point point = pointRepository.findByUsersAndTargetId(users, requestDto.getReviewId());
        if(point == null){//포인트가 없을 경우
            throw new FailedUpdatePointException();
        }
        Long existPoint = point.getPoint(); //수정 전 포인트
        point.updatePoint(mileage);

        if(mileage - existPoint == 0) //포인트 업데이트 안해도 됨
            return;

        //포인트 업데이트
        updatePoint(requestDto.getType(),requestDto.getAction(), mileage - existPoint, requestDto.getReviewId(), users);
    }

    @Transactional
    public void deleteReviewPoint(RequestPoint.register requestDto){
        Users users = usersRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Point point = pointRepository.findByUsersAndTargetId(users, requestDto.getReviewId());
        if(point == null){//포인트가 없을 경우
            throw new FailedUpdatePointException();
        }
        Long mileage = -point.getPoint();

        //포인트 삭제
        pointRepository.delete(point);
        //포인트 업데이트
        updatePoint(requestDto.getType(),requestDto.getAction(), mileage, requestDto.getReviewId(), users);

    }
    @Transactional
    public ResponsePoint.getPointHistory getPointHistory(String userId){
        Users users = usersRepository.findById(userId).orElseThrow(()-> new NotFoundUserException());

        PointTotal pointTotal = pointTotalRepository.findByUsers(users);

        List<History> historyList = new ArrayList<>();
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUsers(users);
        for(PointHistory item : pointHistoryList){
            History history = History.builder()
                    .createdAt(item.getCreatedAt())
                    .eventType(item.getEventType())
                    .actionType(item.getActionType())
                    .targetId(item.getTargetId())
                    .point(item.getPoint())
                    .build();
            historyList.add(history);
        }
        ResponsePoint.getPointHistory response = ResponsePoint.getPointHistory.builder()
                .pointTotal(pointTotal.getTotal())
                .historyList(historyList)
                .build();
        return response;
    }

    @Transactional
    private void updatePoint(String eventType, String actionType,
                             Long mileage, String targetId, Users users){
        //포인트 히스토리에 등록
        PointHistory history = PointHistory.builder()
                .eventType(EventType.valueOf(eventType))
                .actionType(ActionType.valueOf(actionType))
                .point(mileage)
                .targetId(targetId)
                .users(users)
                .build();
        pointHistoryRepository.save(history);

        //포인트 총합 업데이트
        List<Point> point = pointRepository.findByUsers(users);
        Long total =0L;
        if(!point.isEmpty()){ //포인트가 있을 경우
          total  = pointRepository.findByTotal(users);
        }
        PointTotal pointTotal = pointTotalRepository.findByUsers(users);
        if(pointTotal == null){
            throw new FailedUpdatePointException();
        }
        pointTotal.updatePointTotal(total);
    }
}
