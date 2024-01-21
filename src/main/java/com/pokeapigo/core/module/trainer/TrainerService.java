package com.pokeapigo.core.module.trainer;

import com.pokeapigo.core.module.trainer.dto.request.TrainerRequest;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import com.pokeapigo.core.module.trainer.util.enums.TrainerRole;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

public interface TrainerService {

    UserDetailsService userDetailsService();

    LimitedTrainerResponse getTrainer(UUID trainerUUID);

    List<LimitedTrainerResponse> getAllTrainers();

    Page<LimitedTrainerResponse> getPagedTrainers(Pageable pageable, String search);

    LimitedTrainerResponse updateTrainerData(UUID trainerUUID, TrainerRequest trainerRequest, Locale locale);

    Boolean updateTrainerRoles(UUID trainerUUID, List<TrainerRole> trainerRoles, Locale locale);

    Boolean deleteTrainer(UUID trainerUUID, Locale locale);

}
