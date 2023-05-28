package com.br.odontoscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {

    public static final String MESSAGE_SUCCESS = "SUCCESS ";
    public static final String MESSAGE_NOT_FOUND = "NOT FOUND ";
    public static final String MESSAGE_ERROR = "HAD ERRORS ";
    public static final String MESSAGE_DELETED = "DELETED ";

    private String message;
    private String status;
}
