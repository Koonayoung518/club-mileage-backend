package com.club.mileage.backend.serivce;

import com.club.mileage.backend.core.serviceInterface.ReviewServiceInterface;
import com.club.mileage.backend.core.type.ReviewType;
import com.club.mileage.backend.entity.Photo;
import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Review;
import com.club.mileage.backend.entity.Users;
import com.club.mileage.backend.exception.Errors.DuplicatedReviewException;
import com.club.mileage.backend.exception.Errors.NotFoundPlaceException;
import com.club.mileage.backend.exception.Errors.NotFoundReviewException;
import com.club.mileage.backend.exception.Errors.NotFoundUserException;
import com.club.mileage.backend.repository.PhotoRepository;
import com.club.mileage.backend.repository.PlaceRepository;
import com.club.mileage.backend.repository.ReviewRepository;
import com.club.mileage.backend.repository.UsersRepository;
import com.club.mileage.backend.web.dto.RequestReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewServiceInterface {
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final PhotoRepository photoRepository;
    private final S3Service s3Service;
    @Override
    @Transactional
    public void registerReview(List<MultipartFile> fileList, RequestReview.register requestDto){
        Users users = usersRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElseThrow(()->new NotFoundPlaceException());

        //조건 - 한 사용자는 장소마다 리뷰를 1개만 작성할 수 있다
        Review review = reviewRepository.findByUsersAndPlace(users, place);
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
                .users(users)
                .place(place)
                .content(requestDto.getContent())
                .reviewType(reviewType)
                .build();
        review = reviewRepository.save(review);
        users.addReview(review);
        place.addReview(review);

        if(!fileList.isEmpty()){//사진이 있을 경우
            for(MultipartFile file : fileList){
                String url = null;
                try{
                    url = s3Service.upload(file, "review");
                }catch (IOException e){
                    System.out.println("S3 등록 실패");
                }
                Photo photo = Photo.builder()
                        .url(url)
                        .review(review)
                        .build();
                photo = photoRepository.save(photo);
                review.addPhoto(photo);
            }


        }
    }
    @Transactional
    @Override
    public void deleteReview(String userId, String reviewId){
        Users users = usersRepository.findById(userId).orElseThrow(()-> new NotFoundUserException());

        Review review = reviewRepository.findByUsersAndReviewId(users, reviewId);
        if(review == null){// 리뷰가 없을 경우
            throw new NotFoundReviewException();
        }
        //리뷰에 사진이 있으면 s3에 저장된 사진 파일 삭제
        if(!review.getPhotoList().isEmpty()){
            for(Photo photo : review.getPhotoList()){
                s3Service.deleteFile(photo.getUrl());
            }
        }

        Place place = review.getPlace();
        place.getReviewList().remove(review);
        users.getReviewList().remove(review);

        reviewRepository.delete(review);

    }
}
