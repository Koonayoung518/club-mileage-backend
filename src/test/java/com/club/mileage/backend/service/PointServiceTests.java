package com.club.mileage.backend.service;

import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.*;
import com.club.mileage.backend.repository.*;
import com.club.mileage.backend.serivce.PointService;
import com.club.mileage.backend.web.dto.RequestPoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class PointServiceTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PointService pointService;

    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(텍스트만 작성 - 성공)")
    void addPointWhenOnlyTextTest(){
        User user = User.builder()
                .nickname("nickname")
                .build();
        user = userRepository.save(user);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .user(user)
                .place(place)
                .reviewType(ReviewType.NOT_FIRST)
                .build();
        review = reviewRepository.save(review);
        //포인트 적립
        RequestPoint.register requestDto = RequestPoint.register.builder()
                .type("REVIEW")
                .action("ADD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .userId(user.getUserId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUser(user);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 1);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(첫 리뷰 + 텍스트만 작성 - 성공)")
    void addPointWhenFirstAndOnlyTextTest(){
        User user = User.builder()
                .nickname("nickname")
                .build();
        user = userRepository.save(user);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .user(user)
                .place(place)
                .reviewType(ReviewType.FIRST)
                .build();
        review = reviewRepository.save(review);
        //포인트 적립
        RequestPoint.register requestDto = RequestPoint.register.builder()
                .type("REVIEW")
                .action("ADD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .userId(user.getUserId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUser(user);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 2);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(텍스트+사진 - 성공)")
    void addPointWhenPhotoAndTextTest(){
        User user = User.builder()
                .nickname("nickname")
                .build();
        user = userRepository.save(user);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .user(user)
                .place(place)
                .reviewType(ReviewType.NOT_FIRST)
                .build();
        review = reviewRepository.save(review);
        //사진
        List<String > attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add("photoId");
        //포인트 적립
        RequestPoint.register requestDto = RequestPoint.register.builder()
                .type("REVIEW")
                .action("ADD")
                .reviewId(review.getReviewId())
                .attachedPhotoIds(attachedPhotoIds)
                .content(review.getContent())
                .userId(user.getUserId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUser(user);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 2);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(첫리뷰+텍스트+사진 - 성공)")
    void addPointWhenPhotoAndTextAndFirstTest(){
        User user = User.builder()
                .nickname("nickname")
                .build();
        user = userRepository.save(user);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .user(user)
                .place(place)
                .reviewType(ReviewType.FIRST)
                .build();
        review = reviewRepository.save(review);
        //사진
        List<String > attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add("photoId");
        //포인트 적립
        RequestPoint.register requestDto = RequestPoint.register.builder()
                .type("REVIEW")
                .action("ADD")
                .reviewId(review.getReviewId())
                .attachedPhotoIds(attachedPhotoIds)
                .content(review.getContent())
                .userId(user.getUserId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUser(user);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 3);
        }
    }
}
