package com.club.mileage.backend.web;

import com.club.mileage.backend.entity.User;
import com.club.mileage.backend.serivce.ReviewService;
import com.club.mileage.backend.serivce.UserService;
import com.club.mileage.backend.web.dto.RequestReview;
import com.club.mileage.backend.web.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/review")
    public ResponseEntity<ResponseMessage> registerReview(@RequestPart(name = "file")List<MultipartFile> fileList,
                                                          @RequestPart(name = "requestDto")RequestReview.register requestDto){
        reviewService.registerReview(fileList,requestDto);

        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("리뷰 등록 성공")
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<ResponseMessage> getMyReview(@RequestBody Map<String, String> userId, @PathVariable String reviewId){
        reviewService.deleteReview(userId.get("userId"), reviewId);

        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("리뷰 삭제 성공")
                .build(), HttpStatus.OK);

    }
}
