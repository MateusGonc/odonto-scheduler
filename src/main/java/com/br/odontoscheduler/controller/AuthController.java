package com.br.odontoscheduler.controller;

import com.br.odontoscheduler.dto.LoginDTO;
import com.br.odontoscheduler.dto.SignupDTO;
import com.br.odontoscheduler.dto.TokenDTO;
import com.br.odontoscheduler.jwt.JwtHelper;
import com.br.odontoscheduler.model.RefreshToken;
import com.br.odontoscheduler.model.User;
import com.br.odontoscheduler.repository.PatientRepository;
import com.br.odontoscheduler.repository.RefreshTokenRepository;
import com.br.odontoscheduler.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO dto) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        TokenDTO tokenDTO = jwtHelper.fillTokenDTO(user, refreshToken);

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/signup")
    @Transactional
    public ResponseEntity<TokenDTO> signup(@Valid @RequestBody SignupDTO dto) {
        User user = new User(dto.getUsername(), passwordEncoder.encode(dto.getPassword()),
                dto.getFullName());
        user = userService.save(user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setOwner(user);
        refreshTokenRepository.save(refreshToken);

        TokenDTO tokenDTO = jwtHelper.fillTokenDTO(user, refreshToken);

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestBody TokenDTO dto) {
        String refreshTokenString = dto.getRefreshToken();

        if (jwtHelper.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            refreshTokenRepository.deleteByOwner_Id(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            return ResponseEntity.ok().build();
        }

        throw new BadCredentialsException("invalid token");
    }

    @PostMapping("/access-token")
    public ResponseEntity<TokenDTO> accessToken(@RequestBody TokenDTO dto) {
        String refreshTokenString = dto.getRefreshToken();
        TokenDTO tokenDTO = new TokenDTO();
        if (jwtHelper.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db
            Optional<User> opUser = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            User user = opUser.isPresent() ? opUser.get() : null;
            Map<String, Date> accessToken = null;

            if (user != null) {
                Date now = new Date();
                tokenDTO = jwtHelper.generateAccessToken(user, now);
                Date refreshTokenExpiry = jwtHelper.recoveryRefreshTokenExpiryDate(refreshTokenString);
                tokenDTO.setRefreshToken(refreshTokenString);
                tokenDTO.setRefreshTokenExpiresIn(refreshTokenExpiry);
                tokenDTO.setUsername(user.getUsername());
                tokenDTO.setUserId(user.getId());
            } else {
                throw new BadCredentialsException("invalid token");
            }

            return ResponseEntity.ok(tokenDTO);
        }
        throw new BadCredentialsException("invalid token");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenDTO> refreshToken(@RequestBody TokenDTO dto) {
        String refreshTokenString = dto.getRefreshToken();

        if (jwtHelper.validateRefreshToken(refreshTokenString)
                && refreshTokenRepository.existsById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString))) {
            // valid and exists in db

            refreshTokenRepository.deleteById(jwtHelper.getTokenIdFromRefreshToken(refreshTokenString));

            Optional<User> opUser = userService.findById(jwtHelper.getUserIdFromRefreshToken(refreshTokenString));
            User user = opUser.isPresent() ? opUser.get() : null;

            if (user == null) {
                throw new BadCredentialsException("invalid token");
            }

            RefreshToken refreshToken = new RefreshToken();
            refreshToken.setOwner(user);
            refreshTokenRepository.save(refreshToken);

            TokenDTO tokenDTO = jwtHelper.fillTokenDTO(user, refreshToken);

            return ResponseEntity.ok(tokenDTO);
        }

        throw new BadCredentialsException("invalid token");
    }
}
