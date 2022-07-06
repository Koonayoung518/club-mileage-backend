package com.club.mileage.backend.service;

import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.*;
import com.club.mileage.backend.repository.*;
import com.club.mileage.backend.serivce.PointService;
import com.club.mileage.backend.web.dto.RequestPoint;
import com.club.mileage.backend.web.dto.ResponsePoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@ActiveProfiles("test")
public class PointServiceTests {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PointHistoryRepository pointHistoryRepository;
    @Autowired
    private PointTotalRepository pointTotalRepository;
    @Autowired
    private PhotoRepository photoRepository;
    @Autowired
    private PointService pointService;

    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(텍스트만 작성 - 성공)")
    void addPointWhenOnlyTextTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 1);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(첫 리뷰 + 텍스트만 작성 - 성공)")
    void addPointWhenFirstAndOnlyTextTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 2);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(텍스트+사진 - 성공)")
    void addPointWhenPhotoAndTextTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 2);
        }
    }

    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(사진만 - 성공)")
    void addPointWhenOnlyPhotoTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 1);
        }
    }

    @Test
    @Transactional
    @DisplayName("리뷰 작성 포인트 적립 테스트(첫리뷰+텍스트+사진 - 성공)")
    void addPointWhenPhotoAndTextAndFirstTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 3);
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 수정 포인트 적립 테스트(사진 추가 - 성공)")
    void modPointWhenPhotoAndTextAndFirstTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        //리뷰 수정(사진 추가)
        List<String > attachedPhotoIds = new ArrayList<>();
        attachedPhotoIds.add("photoId");
        //포인트 적립
        RequestPoint.register requestDto1 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("MOD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(attachedPhotoIds)
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.modReviewPoint(requestDto1);
        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 2);
        }
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUsers(users);

        for(PointHistory pointHistory: pointHistoryList){
            System.out.println(pointHistory.getPoint()+" "+pointHistory.getActionType());
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 수정 포인트 적립 테스트(사진 삭제 - 성공)")
    void modPointWhenDeletePhotoTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        //리뷰 수정(사진 삭제)
        //포인트 적립
        RequestPoint.register requestDto1 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("MOD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.modReviewPoint(requestDto1);

        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(point.getPoint(), 1);
        }
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUsers(users);

        for(PointHistory pointHistory: pointHistoryList){
            System.out.println(pointHistory.getPoint()+" "+pointHistory.getActionType());
        }
    }
    @Test
    @Transactional
    @DisplayName("리뷰 수정 포인트 적립 테스트(텍스트 수정 - 성공)")
    void modPointWhenModifyTextTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        //포인트 적립
        RequestPoint.register requestDto1 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("MOD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(attachedPhotoIds)
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.modReviewPoint(requestDto1);

        List<Point> pointList = pointRepository.findByUsers(users);
        for(Point point : pointList){
            assertEquals(2, point.getPoint());
        }
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUsers(users);
        assertEquals(1, pointHistoryList.size());

        PointTotal total = pointTotalRepository.findByUsers(users);
        assertEquals(2, total.getTotal());
    }
    @Test
    @Transactional
    @DisplayName("리뷰 삭제 포인트 적립 테스트(텍스트만 있는 리뷰- 성공)")
    void deletePointWhenOnyTextTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);

        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        //리뷰 삭제
        reviewRepository.delete(review);
        //포인트 삭제
        RequestPoint.register requestDto1 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("DELETE")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(attachedPhotoIds)
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.deleteReviewPoint(requestDto1);

        List<Point> pointList = pointRepository.findByUsers(users);
        assertEquals(0,pointList.size());
        List<PointHistory> pointHistoryList = pointHistoryRepository.findByUsers(users);

        for(PointHistory pointHistory: pointHistoryList){
            System.out.println(pointHistory.getPoint()+" "+pointHistory.getActionType());
        }
        assertEquals(2, pointHistoryList.size());
        PointTotal total = pointTotalRepository.findByUsers(users);
        assertEquals(0, total.getTotal());
    }

    @Test
    @Transactional
    @DisplayName("리뷰 삭제 포인트 적립 테스트(성공)")
    void getPointHistoryTest(){
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);

        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //리뷰 작성
        Review review = Review.builder()
                .content("리뷰 내용")
                .users(users)
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
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.addReviewPoint(requestDto);
        //리뷰 수정
        RequestPoint.register requestDto1 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("MOD")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.modReviewPoint(requestDto1);
        //리뷰 삭제
        reviewRepository.delete(review);
        //포인트 삭제
        RequestPoint.register requestDto2 = RequestPoint.register.builder()
                .type("REVIEW")
                .action("DELETE")
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(attachedPhotoIds)
                .userId(users.getUsersId())
                .placeId(place.getPlaceId())
                .build();
        pointService.deleteReviewPoint(requestDto2);

        ResponsePoint.getPointHistory response = pointService.getPointHistory(requestDto.getUserId());

        System.out.println(response);
    }

}
