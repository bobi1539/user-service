package com.zero.service.controller;

import com.zero.service.error.DuplicateException;
import com.zero.service.dto.response.ABaseResponse;
import com.zero.service.error.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<?> nullOrBlank(NullPointerException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ABaseResponse(400, exception.getMessage())
        );
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<?> unauthorized(UnAuthorizedException exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ABaseResponse(401, exception.getMessage())
        );
    }

    @ExceptionHandler(value = DuplicateException.class)
    public ResponseEntity<?> duplicated(DuplicateException exception){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                new ABaseResponse(409, exception.getMessage())
        );
    }
}
