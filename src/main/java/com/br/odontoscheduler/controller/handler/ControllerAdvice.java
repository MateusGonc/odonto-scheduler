package com.br.odontoscheduler.controller.handler;

import com.br.odontoscheduler.dto.ExceptionDTO;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.webjars.NotFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionDTO> NotFoundException(NotFoundException e){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ExceptionDTO(HttpStatus.NOT_FOUND, e.getMessage())
        );
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public ResponseEntity<ExceptionDTO> ForbiddenException(HttpClientErrorException.Forbidden e){

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ExceptionDTO(HttpStatus.FORBIDDEN, e.getMessage())
        );
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<ExceptionDTO> BadRequestException(HttpClientErrorException.BadRequest e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionDTO> IllegalArgumentException(IllegalArgumentException e){

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDTO> DuplicateKeyException(DuplicateKeyException e) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ExceptionDTO(HttpStatus.BAD_REQUEST, e.getMessage())
        );
    }
}
