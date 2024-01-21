package com.pokeapigo.core.module.trainer.impl;

import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.TrainerService;
import com.pokeapigo.core.module.trainer.dto.request.TrainerRequest;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import com.pokeapigo.core.module.trainer.util.enums.TrainerRole;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    private final MessageSource messageSource;

    public TrainerServiceImpl(TrainerRepository trainerRepository, MessageSource messageSource) {
        this.trainerRepository = trainerRepository;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> trainerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public LimitedTrainerResponse getTrainer(UUID trainerUUID) {
        return null;
    }

    @Override
    public List<LimitedTrainerResponse> getAllTrainers() {
        return null;
    }

    @Override
    public Page<LimitedTrainerResponse> getPagedTrainers(Pageable pageable, String search) {
        return null;
    }

    @Override
    public LimitedTrainerResponse updateTrainerData(UUID trainerUUID, TrainerRequest trainerRequest, Locale locale) {
        return null;
    }

    @Override
    public Boolean updateTrainerRoles(UUID trainerUUID, List<TrainerRole> trainerRoles, Locale locale) {
        return null;
    }

    @Override
    public Boolean deleteTrainer(UUID trainerUUID, Locale locale) {
        return null;
    }

}
