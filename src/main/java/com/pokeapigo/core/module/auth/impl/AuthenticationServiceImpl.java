package com.pokeapigo.core.module.auth.impl;

import com.pokeapigo.core.module.auth.AuthenticationService;
import com.pokeapigo.core.module.auth.JwtService;
import com.pokeapigo.core.module.auth.dto.request.LoginRequest;
import com.pokeapigo.core.module.auth.dto.request.RegisterRequest;
import com.pokeapigo.core.module.auth.dto.response.JwtAuthenticationResponse;
import com.pokeapigo.core.module.auth.exception.EmailOrNameAlreadyInUseException;
import com.pokeapigo.core.module.auth.exception.EmailOrPasswordMismatch;
import com.pokeapigo.core.module.auth.exception.PasswordsDoNotMatchException;
import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.util.TrainerUtils;
import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import com.pokeapigo.core.role.RoleEntity;
import com.pokeapigo.core.role.util.enums.TrainerRole;
import jakarta.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Locale;
import java.util.Set;

import static com.pokeapigo.core.common.utli.PokeApiUtils.setEngLocaleIfNull;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final TrainerRepository trainerRepository;
    private final TrainerUtils trainerUtils;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validator validator;
    private final MessageSource messageSource;
    Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    public AuthenticationServiceImpl(TrainerRepository trainerRepository,
                                     TrainerUtils trainerUtils,
                                     PasswordEncoder passwordEncoder,
                                     JwtService jwtService,
                                     AuthenticationManager authenticationManager,
                                     Validator validator,
                                     MessageSource messageSource) {
        this.trainerRepository = trainerRepository;
        this.trainerUtils = trainerUtils;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse register(RegisterRequest request, Locale locale) {
        logger.info("Received register request for user: {}", request.email());
        validator.validate(request);
        locale = setEngLocaleIfNull(locale);

        String email = request.email();
        String name = request.name();

        boolean passwordsMatch = checkIfPasswordsMatch(request.password(), request.confirmPassword());
        if (!passwordsMatch) {
            throw new PasswordsDoNotMatchException(messageSource.getMessage(
                    "auth.passwordsDoNotMatch", null, locale
            ));
        }
        logger.info("Passwords matched. Proceeding with register request for user: {}", email);

        if (trainerRepository.existsEmailOrName(email, name)) {
            throw new EmailOrNameAlreadyInUseException(messageSource.getMessage(
                    "auth.emailOrNameAlreadyInUse", null, locale
            ));
        }

        final TrainerEntity trainerEntity = new TrainerEntity.TrainerEntityBuilder()
                .setName(name)
                .setLevel(1)
                .setTeam(TrainerTeam.NONE)
                .setFriendCode(trainerUtils.generateFriendCode(locale))
                .setEmail(email)
                .setPassword(passwordEncoder.encode(request.password()))
                .setRoles(Set.of(new RoleEntity(TrainerRole.USER)))
                .build();
        trainerRepository.save(trainerEntity);
        final String jwt = jwtService.generateToken(trainerEntity);

        return new JwtAuthenticationResponse(jwt);
    }

    @Override
    public JwtAuthenticationResponse login(LoginRequest request, Locale locale) {
        validator.validate(request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );
        final Locale finalLocale = setEngLocaleIfNull(locale);

        final TrainerEntity userDetails = trainerRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailOrPasswordMismatch(messageSource.getMessage(
                        "auth.emailOrPasswordMismatch", null, finalLocale
                )));
        final String jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }

    private boolean checkIfPasswordsMatch(String password, String confirmPassword) {
        return StringUtils.equals(password, confirmPassword);
    }
}
