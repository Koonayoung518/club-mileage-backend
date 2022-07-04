package com.club.mileage.backend.serivce;

import com.club.mileage.backend.core.serviceInterface.ReviewServiceInterface;
import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Review;
import com.club.mileage.backend.entity.User;
import com.club.mileage.backend.exception.Errors.DuplicatedReviewException;
import com.club.mileage.backend.exception.Errors.NotFoundPlaceException;
import com.club.mileage.backend.exception.Errors.NotFoundUserException;
import com.club.mileage.backend.repository.PlaceRepository;
import com.club.mileage.backend.repository.ReviewRepository;
import com.club.mileage.backend.repository.UserRepository;
import com.club.mileage.backend.web.dto.RequestReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewServiceInterface {
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    @Transactional
    public void registerReview(String userId, List<MultipartFile> fileList,
                                                  RequestReview.register requestDto){
        User user = userRepository.findById(userId).orElseThrow(()-> new NotFoundUserException());
        //명소가 있다는 가정하에
        Place place = placeRepository.findById(requestDto.getPlaceId()).orElseThrow(()->new NotFoundPlaceException());

        //조건 - 한 사용자는 장소마다 리뷰를 1개만 작성할 수 있다
        Review review = reviewRepository.findByUserAndPlace(user, place);
        if(review != null){ //이미 리뷰가 있을 경우
            throw new DuplicatedReviewException();
        }

        //해당 장소의 첫 리뷰인지 확인
        ReviewType reviewType;
        if(place.getReviewList().isEmpty()){ //첫 리뷰면
            reviewType = ReviewType.FIRST;
        }else
            reviewType = ReviewType.NOT_FIRST;

        //리뷰 등록
        review = Review.builder()
                .user(user)
                .content(requestDto.getContent())
                .reviewType(reviewType)
                .build();
        reviewRepository.save(review);
    }
}
