package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class DuplicatedPointException extends RuntimeException{
    public DuplicatedPointException(){
        super(ErrorCode.DUPLICATED_POINT.getMessage());
    }
}
