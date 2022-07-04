package com.club.mileage.backend.core.serviceInterface;

import com.club.mileage.backend.web.dto.RequestReview;
import com.club.mileage.backend.web.dto.ResponseReview;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ReviewServiceInterface {
    void registerReview(String userId, List<MultipartFile> fileList, RequestReview.register requestDto);

}