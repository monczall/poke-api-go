package com.pokeapigo.core.module.trainer;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface TrainerService {

    UserDetailsService userDetailsService();
}
