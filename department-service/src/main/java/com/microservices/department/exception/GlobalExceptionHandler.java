package com.microservices.department.exception;

import com.microservices.department.payload.APIErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest){
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<APIErrorDetails> handleNumberFormatException(DepartmentNotFoundException exception,
                                                                       WebRequest webRequest) {
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(apiErrorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIErrorDetails> handleGlobalException(Exception exception,
                                                                 WebRequest webRequest){
        APIErrorDetails apiErrorDetails = new APIErrorDetails(new Date(),
                exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<APIErrorDetails>(apiErrorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}