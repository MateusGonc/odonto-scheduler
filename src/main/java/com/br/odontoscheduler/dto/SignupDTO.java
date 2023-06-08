package com.br.odontoscheduler.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupDTO {
    @NotBlank
    @Size(min = 4, max = 30)
    private String username;

    @NotBlank
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank
    private String fullName;
}
