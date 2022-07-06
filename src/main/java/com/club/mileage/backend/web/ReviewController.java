package com.club.mileage.backend.web;

import com.club.mileage.backend.entity.Place;
import com.club.mileage.backend.entity.Users;
import com.club.mileage.backend.serivce.PlaceService;
import com.club.mileage.backend.serivce.ReviewService;
import com.club.mileage.backend.serivce.UserService;
import com.club.mileage.backend.web.dto.RequestReview;
import com.club.mileage.backend.web.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewController {
    private final UserService userService;
    private final PlaceService placeService;
    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity<ResponseMessage> registerReview(@RequestPart(name = "file")List<MultipartFile> fileList,
                                                          @RequestPart(name = "requestDto")RequestReview.register requestDto){
        reviewService.registerReview(fileList,requestDto);

        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("리뷰 등록 성공")
                .build(), HttpStatus.OK);
    }
    @DeleteMapping("/review/{userId}/{reviewId}")
    public ResponseEntity<ResponseMessage> getMyReview(@PathVariable String userId, @PathVariable String reviewId){
        reviewService.deleteReview(userId, reviewId);

        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("리뷰 삭제 성공")
                .build(), HttpStatus.OK);

    }

    @PostMapping("/mock")
    public ResponseEntity<ResponseMessage> makeMock(){
        Users users = userService.mockUser();
        Place place = placeService.mockPlace();
        Map<String, String> map = new HashMap<>();
        map.put("userId", users.getUsersId());
        map.put("placeId", place.getPlaceId());
        return new ResponseEntity<>(ResponseMessage.builder()
                .status(HttpStatus.OK.value())
                .message("mock 등록 성공")
                .list(map)
                .build(), HttpStatus.OK);
    }
}
