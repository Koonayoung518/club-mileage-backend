package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class FailedUpdatePointException extends RuntimeException{
    public FailedUpdatePointException(){
        super(ErrorCode.FAILED_UPDATE_POINT.getMessage());
    }
}
