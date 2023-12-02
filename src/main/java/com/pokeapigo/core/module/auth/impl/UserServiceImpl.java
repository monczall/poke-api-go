package com.pokeapigo.core.module.auth.impl;

import com.pokeapigo.core.module.auth.UserService;
import com.pokeapigo.core.module.trainer.TrainerRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final TrainerRepository trainerRepository;

    public UserServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> trainerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
