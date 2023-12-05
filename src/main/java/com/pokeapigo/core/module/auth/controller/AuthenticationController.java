package com.pokeapigo.core.module.auth.controller;

import com.pokeapigo.core.module.auth.AuthenticationService;
import com.pokeapigo.core.module.auth.dto.request.LoginRequest;
import com.pokeapigo.core.module.auth.dto.request.RegisterRequest;
import com.pokeapigo.core.module.auth.dto.response.JwtAuthenticationResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.*;

@RestController
@RequestMapping(API_URI_V1 + API_AUTH)
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(API_REGISTER)
    public ResponseEntity<JwtAuthenticationResponse> register(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @Valid @RequestBody RegisterRequest request
    ) {
        final JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.register(request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwtAuthenticationResponse);
    }

    @PostMapping(API_LOGIN)
    public ResponseEntity<JwtAuthenticationResponse> login(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @Valid @RequestBody LoginRequest request
    ) {
        final JwtAuthenticationResponse jwtAuthenticationResponse = authenticationService.login(request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jwtAuthenticationResponse);
    }
}
