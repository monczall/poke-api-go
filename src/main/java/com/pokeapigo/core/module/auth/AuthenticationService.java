package com.pokeapigo.core.module.auth;

import com.pokeapigo.core.module.auth.dto.request.LoginRequest;
import com.pokeapigo.core.module.auth.dto.request.RegisterRequest;
import com.pokeapigo.core.module.auth.dto.response.JwtAuthenticationResponse;

import java.util.Locale;

public interface AuthenticationService {
    JwtAuthenticationResponse register(RegisterRequest request, Locale locale);

    JwtAuthenticationResponse login(LoginRequest request, Locale locale);

}
