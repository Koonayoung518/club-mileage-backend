package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class NotFoundReviewException extends RuntimeException{
    public NotFoundReviewException(){
        super(ErrorCode.NOT_FOUND_REVIEW.getMessage());
    }
}
