package com.br.odontoscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ExceptionDTO {

    private HttpStatus status;
    private String message;

}
