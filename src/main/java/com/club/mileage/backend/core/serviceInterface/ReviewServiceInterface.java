package com.club.mileage.backend.core.serviceInterface;

import com.club.mileage.backend.web.dto.RequestReview;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ReviewServiceInterface {
    void registerReview(List<MultipartFile> fileList, RequestReview.register requestDto);
    void updateReview(List<MultipartFile> fileList, RequestReview.update requestDto);
    void deleteReview(String userId, String reviewId);
}
