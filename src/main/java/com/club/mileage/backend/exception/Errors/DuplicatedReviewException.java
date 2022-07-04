package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class DuplicatedReviewException extends RuntimeException{
    public DuplicatedReviewException(){
        super(ErrorCode.DUPLICATED_REVIEW.getMessage());
    }
}
