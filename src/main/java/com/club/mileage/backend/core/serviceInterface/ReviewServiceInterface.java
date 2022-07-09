package com.club.mileage.backend.core.serviceInterface;

import com.club.mileage.backend.web.dto.RequestReview;
import com.club.mileage.backend.web.dto.ResponseReview;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ReviewServiceInterface {
    ResponseReview.review registerReview(List<MultipartFile> fileList, RequestReview.register requestDto);
    ResponseReview.review updateReview(List<MultipartFile> fileList, RequestReview.update requestDto);
    ResponseReview.review deleteReview(String userId, String reviewId);
    List<ResponseReview.getMyReview> getMyReview(String userId);
}
