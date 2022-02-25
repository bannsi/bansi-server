package com.gotgam.bansi.exception;

import com.gotgam.bansi.DTO.ResponseDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.webjars.NotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException e){
        log.info(e.getMessage());
        final ResponseDTO response = new ResponseDTO("ERROR_100", e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }    
}
