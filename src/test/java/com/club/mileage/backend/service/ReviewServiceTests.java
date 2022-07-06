package com.club.mileage.backend.service;

import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Review;
import com.club.mileage.backend.entity.Users;
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
        System.out.println(review.getContent()+" "+ review.getPhotoList());
    }


    }
