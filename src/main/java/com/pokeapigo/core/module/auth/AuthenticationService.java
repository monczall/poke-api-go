package com.pokeapigo.core.module.auth;

import com.pokeapigo.core.module.auth.dto.request.SignInRequest;
import com.pokeapigo.core.module.auth.dto.request.SignUpRequest;
import com.pokeapigo.core.module.auth.dto.response.JwtAuthenticationResponse;

import java.util.Locale;

public interface AuthenticationService {
    JwtAuthenticationResponse signUp(SignUpRequest request, Locale locale);

    JwtAuthenticationResponse signIn(SignInRequest request, Locale locale);

}
