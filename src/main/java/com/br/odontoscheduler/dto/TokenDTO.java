package com.br.odontoscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {
    private String username;
    private String accessToken;
    private String refreshToken;
    private Date AccessTokenExpiresIn;
    private Date RefreshTokenExpiresIn;
}