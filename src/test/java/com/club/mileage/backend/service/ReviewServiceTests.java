package com.club.mileage.backend.service;

import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Review;
import com.club.mileage.backend.entity.Users;
import com.club.mileage.backend.exception.Errors.DuplicatedReviewException;
import com.club.mileage.backend.repository.PhotoRepository;
import com.club.mileage.backend.repository.PlaceRepository;
import com.club.mileage.backend.repository.ReviewRepository;
import com.club.mileage.backend.repository.UsersRepository;
import com.club.mileage.backend.serivce.ReviewService;
import com.club.mileage.backend.web.dto.RequestReview;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReviewServiceTests {
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PhotoRepository photoRepository;

    @Test
    @Transactional
    @DisplayName("리뷰 작성 테스트(텍스트+사진 - 성공)")
    void registerReviewWhenTextAndPhotoTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //사진
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test1.png",
                "image/png", "test data".getBytes());
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", "test2.png",
                "image/png", "test data".getBytes());
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile1);

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);

        Review review = reviewRepository.findByUsersAndPlace(users, place);
        assertEquals(2, review.getPhotoList().size());
    }
    @Test
    @Transactional
    @DisplayName("리뷰 작성 테스트(이미 리뷰 있는 경우 - 실패)")
    void registerReviewWhenAlreadyExistReviewTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);

        //사진
        List<MultipartFile> fileList = new ArrayList<>();

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);
        assertThrows(DuplicatedReviewException.class,()-> reviewService.registerReview(fileList, requestDto));
    }
    @Test
    @Transactional
    @DisplayName("리뷰 수정 테스트(텍스트, 사진 수정 - 성공)")
    void updateReviewWhenModifyTextTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //사진
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test1.png",
                "image/png", "test data".getBytes());
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", "test2.png",
                "image/png", "test data".getBytes());
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile1);

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);

        //리뷰 수정
        List<MultipartFile> fileList1 = new ArrayList<>();
        RequestReview.update requestDto1 = RequestReview.update.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 수정 내용")
                .build();
        reviewService.updateReview(fileList1, requestDto1);

        Review review = reviewRepository.findByUsersAndPlace(users, place);
        assertEquals("리뷰 수정 내용", review.getContent());
        assertEquals(0, photoRepository.findByReview(review).size());
    }
    @Test
    @Transactional
    @DisplayName("리뷰 수정 테스트(사진 수 추가 수정 - 성공)")
    void updateReviewWhenIncreasePhotoTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //사진
        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test1.png",
                "image/png", "test data".getBytes());
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", "test2.png",
                "image/png", "test data".getBytes());
        List<MultipartFile> fileList = new ArrayList<>();
        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile1);

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);

        //리뷰 수정
        RequestReview.update requestDto1 = RequestReview.update.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 수정 내용")
                .build();


        MockMultipartFile mockMultipartFile2 = new MockMultipartFile("file", "test3.png",
                "image/png", "test data".getBytes());
        fileList.add(mockMultipartFile2);

        reviewService.updateReview(fileList, requestDto1);

        Review review = reviewRepository.findByUsersAndPlace(users, place);
        assertEquals("리뷰 수정 내용", review.getContent());
        assertEquals(3, photoRepository.findByReview(review).size());
    }

    @Test
    @Transactional
    @DisplayName("리뷰 수정 테스트(사진 없을 때 사진 추가하는 수정 - 성공)")
    void updateReviewWhenNotExistPhotoTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //사진
        List<MultipartFile> fileList = new ArrayList<>();

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);

        //리뷰 수정
        RequestReview.update requestDto1 = RequestReview.update.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 수정 내용")
                .build();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test1.png",
                "image/png", "test data".getBytes());
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", "test2.png",
                "image/png", "test data".getBytes());

        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile1);

        reviewService.updateReview(fileList, requestDto1);

        Review review = reviewRepository.findByUsersAndPlace(users, place);
        assertEquals("리뷰 수정 내용", review.getContent());
        assertEquals(2, photoRepository.findByReview(review).size());
    }

    @Test
    @Transactional
    @DisplayName("리뷰 삭제 테스트(사진+텍스트 - 성공)")
    void deleteReviewWhenTextAndPhotoTest() {
        Users users = Users.builder()
                .nickname("nickname")
                .build();
        users = usersRepository.save(users);
        //장소
        Place place = Place.builder()
                .placeName("테스트 장소")
                .build();
        place = placeRepository.save(place);
        //사진
        List<MultipartFile> fileList = new ArrayList<>();

        MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "test1.png",
                "image/png", "test data".getBytes());
        MockMultipartFile mockMultipartFile1 = new MockMultipartFile("file", "test2.png",
                "image/png", "test data".getBytes());

        fileList.add(mockMultipartFile);
        fileList.add(mockMultipartFile1);

        RequestReview.register requestDto = RequestReview.register.builder()
                .placeId(place.getPlaceId())
                .userId(users.getUsersId())
                .content("리뷰 내용")
                .build();
        reviewService.registerReview(fileList, requestDto);

        Review review = reviewRepository.findByUsersAndPlace(users, place);

        //리뷰 삭제
        reviewService.deleteReview(users.getUsersId(),review.getReviewId());

        assertNull(reviewRepository.findByUsersAndPlace(users, place));
        assertEquals(0, photoRepository.findAll().size());
    }
    }
