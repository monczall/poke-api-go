package com.pokeapigo.core.module.auth.config;

import com.pokeapigo.core.module.trainer.TrainerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.pokeapigo.core.role.util.enums.TrainerRole.ADMIN;
import static com.pokeapigo.core.role.util.enums.TrainerRole.USER;

@Configuration
public class SecurityConfiguration {

    private static final String ROLE_ADMIN = ADMIN.name();
    public static final String ROLE_USER = USER.name();

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final TrainerService trainerService;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, TrainerService trainerService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.trainerService = trainerService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(this::configureOpenEndpoints)
                .authorizeHttpRequests(this::configurePokemonAuthorization)
                .authorizeHttpRequests(this::configureTrainerAuthorization)
                .authorizeHttpRequests(request -> request.anyRequest().hasAuthority(ROLE_ADMIN))
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

    private void configureOpenEndpoints(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request
    ) {
        request
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register", "/api/v1/auth/login")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/actuator", "/actuator/health")
                .permitAll()

                .requestMatchers("**")
                .permitAll();
    }

    private void configurePokemonAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request
    ) {
        request
                .requestMatchers(HttpMethod.POST, "/api/v1/pokemons", "/api/v1/pokemons/csv")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.GET, "/api/v1/pokemons/secured")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.GET, "/api/v1/pokemons", "/api/v1/pokemons/*")
                .hasAuthority(ROLE_USER)

                .requestMatchers(HttpMethod.PUT, "/api/v1/pokemons/**")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.PATCH, "/api/v1/pokemons/**")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.DELETE, "/api/v1/pokemons/**")
                .hasAuthority(ROLE_ADMIN);
    }

    private void configureTrainerAuthorization(
            AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry request
    ) {
        request
                .requestMatchers(HttpMethod.GET, "/api/v1/trainers/secured")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.GET, "/api/v1/trainers/**")
                .hasAuthority(ROLE_USER)

                .requestMatchers(HttpMethod.PUT, "/api/v1/trainers/**")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.PATCH, "/api/v1/trainers/**")
                .hasAuthority(ROLE_ADMIN)

                .requestMatchers(HttpMethod.DELETE, "/api/v1/trainers/**")
                .hasAuthority(ROLE_ADMIN);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(trainerService.userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
