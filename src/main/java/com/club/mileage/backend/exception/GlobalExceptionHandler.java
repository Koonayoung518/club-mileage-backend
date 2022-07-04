package com.club.mileage.backend.exception;

import com.club.mileage.backend.exception.Errors.*;
import com.club.mileage.backend.web.dto.ResponseMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(NotFoundUserException.class)
    protected ResponseEntity<ResponseMessage> handleNotFoundUserException(NotFoundUserException e) {
        ErrorCode code = ErrorCode.NOT_FOUND_USER;

        ResponseMessage response = ResponseMessage.builder()
                .status(code.getHttpStatus().value())
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
    @ExceptionHandler(NotFoundReviewException.class)
    protected ResponseEntity<ResponseMessage> handleNotFoundReviewException(NotFoundReviewException e) {
        ErrorCode code = ErrorCode.NOT_FOUND_REVIEW;

        ResponseMessage response = ResponseMessage.builder()
                .status(code.getHttpStatus().value())
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
    @ExceptionHandler(NotFoundPlaceException.class)
    protected ResponseEntity<ResponseMessage> handleNotFoundPlaceException(NotFoundPlaceException e) {
        ErrorCode code = ErrorCode.NOT_FOUND_PLACE;

        ResponseMessage response = ResponseMessage.builder()
                .status(code.getHttpStatus().value())
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
    @ExceptionHandler(DuplicatedReviewException.class)
    protected ResponseEntity<ResponseMessage> handleDuplicatedReviewException(DuplicatedReviewException e) {
        ErrorCode code = ErrorCode.DUPLICATED_REVIEW;

        ResponseMessage response = ResponseMessage.builder()
                .status(code.getHttpStatus().value())
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
    @ExceptionHandler(FailedUpdatePointException.class)
    protected ResponseEntity<ResponseMessage> handleFailedUpdatePointException(FailedUpdatePointException e) {
        ErrorCode code = ErrorCode.FAILED_UPDATE_POINT;

        ResponseMessage response = ResponseMessage.builder()
                .status(code.getHttpStatus().value())
                .message(code.getMessage())
                .list(code.getCode())
                .build();
        return new ResponseEntity<>(response, code.getHttpStatus());
    }
}
