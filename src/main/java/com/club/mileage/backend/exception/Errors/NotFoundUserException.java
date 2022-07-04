package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class NotFoundUserException extends RuntimeException{
    public NotFoundUserException(){
        super(ErrorCode.NOT_FOUND_USER.getMessage());
    }
}
