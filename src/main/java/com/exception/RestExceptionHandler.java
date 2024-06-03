package com.exception;


import com.dto.ErrorsDTO;

import com.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity<ErrorResponse> handleException(AuthException ex) {
        ErrorsDTO errors = new ErrorsDTO(List.of(ex.getMessage()));
        return ResponseEntity.status(ex.getHttpStatus())
                .body(new ErrorResponse(errors));
    }
}
