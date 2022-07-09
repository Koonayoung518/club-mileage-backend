package com.club.mileage.backend.serivce;

import com.club.mileage.backend.core.serviceInterface.ReviewServiceInterface;
import com.club.mileage.backend.core.type.ActionType;
import com.club.mileage.backend.core.type.EventType;
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
import com.club.mileage.backend.web.dto.ResponseReview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewServiceInterface {
    private final UsersRepository usersRepository;
    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final PhotoRepository photoRepository;
    private final S3Service s3Service;
    private final WebClient pointWebClient;
    @Override
    @Transactional
    public ResponseReview.review registerReview(List<MultipartFile> fileList, RequestReview.register requestDto){
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

        if(fileList !=null && !fileList.isEmpty()){ //사진이 있을 경우
            for(MultipartFile file : fileList){
                try{
                    String url = s3Service.upload(file, "review");

                    Photo photo = Photo.builder()
                            .url(url)
                            .review(review)
                            .build();
                    photo = photoRepository.save(photo);
                    review.addPhoto(photo);
                }catch (IOException e){
                    System.out.println("S3 등록 실패");
                }
            }
        }
        ResponseReview.review response = getResult(ActionType.ADD.toString(),review);
        return response;
    }

    @Override
    @Transactional
    public ResponseReview.review updateReview(List<MultipartFile> fileList, RequestReview.update requestDto){
        Users users = usersRepository.findById(requestDto.getUserId()).orElseThrow(()-> new NotFoundUserException());

        Place place = placeRepository.findById(requestDto.getPlaceId()).orElseThrow(()->new NotFoundPlaceException());

        Review review = reviewRepository.findByUsersAndPlace(users, place);
        if(review == null){
            throw new NotFoundReviewException();
        }
        //내용업데이트
        review.updateReview(Optional.ofNullable(requestDto.getContent()).orElse(null));

        //기존 리뷰에 사진이 있으면 s3에 저장된 사진 파일 삭제
        List<Photo> photoList = photoRepository.findByReview(review);
        if(photoList != null && !photoList.isEmpty()){
            for(Photo photo : photoList){
                if(photo.getUrl() != null)
                    s3Service.deleteFile(photo.getUrl());

                review.getPhotoList().remove(photo);
                photoRepository.delete(photo);
            }
        }
        if(fileList != null && !fileList.isEmpty()){ //수정한 리뷰에 사진이 있을 경우
            for(MultipartFile file : fileList){
                try{
                    String url = s3Service.upload(file, "review");
                    Photo photo = Photo.builder()
                            .url(url)
                            .review(review)
                            .build();
                    photo = photoRepository.save(photo);
                    review.addPhoto(photo);
                }catch (IOException e){
                    System.out.println("S3 등록 실패");
                }
            }
        }
        ResponseReview.review response = getResult(ActionType.MOD.toString(),review);
        return response;
    }

    @Transactional
    @Override
    public ResponseReview.review deleteReview(String userId, String reviewId){
        Users users = usersRepository.findById(userId).orElseThrow(()-> new NotFoundUserException());

        Review review = reviewRepository.findByUsersAndReviewId(users, reviewId);
        if(review == null){// 리뷰가 없을 경우
            throw new NotFoundReviewException();
        }
        //리뷰에 사진이 있으면 s3에 저장된 사진 파일 삭제
        if(review.getPhotoList() != null && !review.getPhotoList().isEmpty()){
            for(Photo photo : review.getPhotoList()){
                if(photo.getUrl() != null)
                s3Service.deleteFile(photo.getUrl());
            }
        }

        Place place = review.getPlace();
        place.getReviewList().remove(review);
        users.getReviewList().remove(review);

        ResponseReview.review response = getResult(ActionType.DELETE.toString(),review);
        reviewRepository.delete(review);

        return response;

    }

    @Transactional
    @Override
    public List<ResponseReview.getMyReview> getMyReview(String userId){
        Users users = usersRepository.findById(userId).orElseThrow(()-> new NotFoundUserException());

        List<ResponseReview.getMyReview> list = new ArrayList<>();
        List<Review> reviewList = reviewRepository.findByUsers(users);

        for(Review review : reviewList){
            ResponseReview.getMyReview dto = ResponseReview.getMyReview.builder()
                    .reviewId(review.getReviewId())
                    .content(review.getContent())
                    .build();
            list.add(dto);
        }

        return list;
    }

    private ResponseReview.review getResult(String actionType, Review review){

        List<String> photoList = new ArrayList<>();
        if(!Optional.ofNullable(review.getPhotoList()).isEmpty()){//사진이 있으면
            for(Photo photo : review.getPhotoList()){
                photoList.add(photo.getPhotoId());
            }
        }
        ResponseReview.review result = ResponseReview.review.builder()
                .type(EventType.REVIEW.toString())
                .action(actionType)
                .reviewId(review.getReviewId())
                .content(review.getContent())
                .attachedPhotoIds(photoList)
                .userId(review.getUsers().getUsersId())
                .placeId(review.getPlace().getPlaceId())
                .build();

        return result;
    }

}
