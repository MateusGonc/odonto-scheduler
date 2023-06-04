package com.br.odontoscheduler.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.br.odontoscheduler.dto.TokenDTO;
import com.br.odontoscheduler.model.RefreshToken;
import com.br.odontoscheduler.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class JwtHelper {

    private static final Logger logger = LoggerFactory.getLogger(JwtHelper.class);
    static final String ISSUER = "MyApp";

    private long accessTokenExpirationMs;
    private long refreshTokenExpirationMs;

    private Algorithm accessTokenAlgorithm;
    private Algorithm refreshTokenAlgorithm;
    private JWTVerifier accessTokenVerifier;
    private JWTVerifier refreshTokenVerifier;

    public JwtHelper(@Value("${accessTokenSecret}") String accessTokenSecret,
            @Value("${refreshTokenSecret}") String refreshTokenSecret,
            @Value("${refreshTokenExpirationDays}") int refreshTokenExpirationDays,
            @Value("${accessTokenExpirationMinutes}") int accessTokenExpirationMinutes) {
        accessTokenExpirationMs = (long) accessTokenExpirationMinutes * 60 * 1000;
        refreshTokenExpirationMs = (long) refreshTokenExpirationDays * 24 * 60 * 60 * 1000;
        accessTokenAlgorithm = Algorithm.HMAC512(accessTokenSecret);
        refreshTokenAlgorithm = Algorithm.HMAC512(refreshTokenSecret);
        accessTokenVerifier = JWT.require(accessTokenAlgorithm)
                .withIssuer(ISSUER)
                .build();
        refreshTokenVerifier = JWT.require(refreshTokenAlgorithm)
                .withIssuer(ISSUER)
                .build();
    }

    public TokenDTO generateAccessToken(User user, Date date) {
        date = new Date((date).getTime() + accessTokenExpirationMs);

        String accessToken = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(date)
                .sign(accessTokenAlgorithm);

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setAccessToken(accessToken);
        tokenDTO.setAccessTokenExpiresIn(date);

        return tokenDTO;
    }

    public TokenDTO generateRefreshToken(User user, RefreshToken refreshToken, Date date, TokenDTO tokenDTO) {
        date = new Date((date).getTime() + refreshTokenExpirationMs);

        String newRefreshToken = JWT.create()
                .withIssuer(ISSUER)
                .withSubject(user.getId())
                .withClaim("tokenId", refreshToken.getId())
                .withIssuedAt(new Date())
                .withExpiresAt(date)
                .sign(refreshTokenAlgorithm);

        tokenDTO.setRefreshToken(newRefreshToken);
        tokenDTO.setRefreshTokenExpiresIn(date);

        return tokenDTO;
    }

    private Optional<DecodedJWT> decodeAccessToken(String token) {
        try {
            return Optional.of(accessTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            logger.error("", e);
        }

        return Optional.empty();
    }

    private Optional<DecodedJWT> decodeRefreshToken(String token) {
        try {
            return Optional.of(refreshTokenVerifier.verify(token));
        } catch (JWTVerificationException e) {
            logger.error("invalid refresh token", e);
        }

        return Optional.empty();
    }

    public boolean validateAccessToken(String token) {
        return decodeAccessToken(token).isPresent();
    }

    public boolean validateRefreshToken(String token) {
        return decodeRefreshToken(token).isPresent();
    }

    public String getUserIdFromAccessToken(String token) {
        return decodeAccessToken(token).get().getSubject();
    }

    public String getUserIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getSubject();
    }

    public String getTokenIdFromRefreshToken(String token) {
        return decodeRefreshToken(token).get().getClaim("tokenId").asString();
    }

    public Date recoveryRefreshTokenExpiryDate(String token){
        if(decodeRefreshToken(token).isPresent()){
            DecodedJWT jwt = decodeRefreshToken(token).get();
            return jwt.getExpiresAt();
        }
        return new Date();
    }

    public TokenDTO fillTokenDTO(User user, RefreshToken refreshToken){
        Date now = new Date();

        TokenDTO tokenDTO = this.generateAccessToken(user, now);
        tokenDTO = this.generateRefreshToken(user, refreshToken, now, tokenDTO);
        tokenDTO.setUsername(user.getUsername());

        return tokenDTO;
    }
}
