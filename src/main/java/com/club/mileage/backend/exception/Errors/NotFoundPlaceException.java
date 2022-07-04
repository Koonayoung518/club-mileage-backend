package com.club.mileage.backend.exception.Errors;

import com.club.mileage.backend.exception.ErrorCode;

public class NotFoundPlaceException extends RuntimeException{
    public NotFoundPlaceException(){
        super(ErrorCode.NOT_FOUND_PLACE.getMessage());
    }
}
