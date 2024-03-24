package com.pokeapigo.core.module.trainer.impl;

import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.TrainerService;
import com.pokeapigo.core.module.trainer.dto.request.TrainerRequest;
import com.pokeapigo.core.module.trainer.dto.response.FullTrainerResponse;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import com.pokeapigo.core.module.trainer.exception.TrainerAlreadyExistsException;
import com.pokeapigo.core.module.trainer.exception.TrainerNotFoundException;
import com.pokeapigo.core.module.trainer.util.TrainerConstants;
import com.pokeapigo.core.module.trainer.util.TrainerMapper;
import com.pokeapigo.core.module.trainer.util.TrainerUtils;
import com.pokeapigo.core.role.RoleEntity;
import com.pokeapigo.core.role.util.enums.TrainerRole;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.pokeapigo.core.common.util.PokeApiUtils.*;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final TrainerUtils trainerUtils;
    private final Validator validator;
    private final MessageSource messageSource;
    Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);

    public TrainerServiceImpl(TrainerRepository trainerRepository, TrainerUtils trainerUtils, Validator validator, MessageSource messageSource) {
        this.trainerRepository = trainerRepository;
        this.trainerUtils = trainerUtils;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    public UserDetailsService userDetailsService() {
        return username -> trainerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public LimitedTrainerResponse getTrainer(UUID trainerUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);

        final TrainerEntity trainerEntity = getTrainerByUUID(trainerUUID, locale);

        return TrainerMapper.toLimitedTrainerResponse(trainerEntity);
    }

    @Override
    public List<FullTrainerResponse> getAllTrainers() {
        logger.warn("Called method to return all trainers from the database!");

        final List<TrainerEntity> trainerList = trainerRepository.findAll();

        return trainerList.stream()
                .map(TrainerMapper::toFullTrainerResponse)
                .toList();
    }

    @Override
    public Page<LimitedTrainerResponse> getPagedTrainers(Pageable pageable, String search, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        pageable = ensureMaxPageSize(pageable, TrainerConstants.TRAINER_PAGE_MAX);
        pageable = applyDefaultSortingIfNone(pageable, "name");

        final Page<TrainerEntity> trainerPage = returnPagedTrainers(pageable, search, locale);

        return TrainerMapper.toPagedLimitedTrainerResponse(trainerPage);
    }

    @Override
    @Transactional
    public FullTrainerResponse updateTrainerData(UUID trainerUUID, TrainerRequest request, Locale locale) {
        validator.validate(request);
        locale = setEngLocaleIfNull(locale);

        logger.info("Started update of trainer with UUID: {}. Data: [Name: {}, Level: {}, Team: {}, avatarUrl: {}]",
                trainerUUID, request.name(), request.level(), request.team(), request.avatarUrl());
        throwIfTrainerAlreadyExists(trainerUUID, request, locale);
        TrainerEntity trainer = getTrainerByUUID(trainerUUID, locale);
        final TrainerEntity result = trainerRepository.save(
                trainerUtils.updateTrainerEntityData(trainer, request, locale)
        );
        logger.info("Finished update of trainer with UUID: {}", trainerUUID);

        return TrainerMapper.toFullTrainerResponse(result);
    }

    @Override
    @Transactional
    public FullTrainerResponse updateTrainerRoles(UUID trainerUUID, List<TrainerRole> trainerRoles, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        TrainerEntity trainer = getTrainerByUUID(trainerUUID, locale);

        logger.info("Started update of trainer role with UUID: {}. New roles: {}", trainerUUID, trainerRoles);
        Set<RoleEntity> roles = trainerRoles.stream()
                .map(RoleEntity::new)
                .collect(Collectors.toSet());
        trainer.setRoles(roles);
        logger.info("Finished update of trainer with UUID: {}", trainerUUID);

        return TrainerMapper.toFullTrainerResponse(null);
    }

    @Override
    @Transactional
    public void disableTrainer(UUID trainerUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        TrainerEntity trainer = getTrainerByUUID(trainerUUID, locale);
        final String maskedEmail = maskEmail(trainer.getUsername());
        final String name = trainer.getName();

        logger.info("About to disable Trainer with UUID: {}, Email: {}, Name: {}", trainerUUID, maskedEmail, name);
        if (!trainer.isEnabled()) {
            logger.info("Trainer with UUID: {}, Email: {}, Name: {} is already disabled. No action taken.",
                    trainerUUID, maskedEmail, name);
            return;
        }
        trainer.setValidIndicator(false);
        logger.info("Trainer {} - {} disabled", maskedEmail, name);
    }

    @Override
    @Transactional
    public void enableTrainer(UUID trainerUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        TrainerEntity trainer = getTrainerByUUID(trainerUUID, locale);
        final String maskedEmail = maskEmail(trainer.getUsername());
        final String name = trainer.getName();

        logger.info("About to enable Trainer with UUID: {}, Email: {}, Name: {}", trainerUUID, maskedEmail, name);
        if (trainer.isEnabled()) {
            logger.info("Trainer with UUID: {}, Email: {}, Name: {} is already enabled. No action taken.",
                    trainerUUID, maskedEmail, name);
            return;
        }
        trainer.setValidIndicator(true);
        logger.info("Trainer {} - {} enabled", maskedEmail, name);
    }

    @Override
    @Transactional
    public void deleteTrainer(UUID trainerUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        TrainerEntity trainer = getTrainerByUUID(trainerUUID, locale);
        final String maskedEmail = maskEmail(trainer.getUsername());
        final String name = trainer.getName();

        logger.info("About to delete Trainer with UUID: {}, Email: {}, Name: {}", trainerUUID, maskedEmail, name);
        trainerRepository.delete(trainer);
        logger.info("Trainer {} - {} - {} deleted", trainerUUID, maskedEmail, name);
    }

    private Page<TrainerEntity> returnPagedTrainers(Pageable pageable, String search, Locale locale) {
        try {
            return trainerRepository.findBySearch(pageable, search);
        } catch (InvalidDataAccessApiUsageException e) {
            throw getCorrectSortingException(e, locale, messageSource);
        }
    }

    private void throwIfTrainerAlreadyExists(UUID trainerUUID, TrainerRequest request, Locale locale) {
        final String requestedName = request.name();
        final boolean trainerAlreadyExists = trainerRepository.editedTrainerNameAlreadyOccupied(
                trainerUUID,
                requestedName
        );

        if (trainerAlreadyExists) {
            throw new TrainerAlreadyExistsException(messageSource.getMessage(
                    "trainer.alreadyExists", new Object[]{trainerUUID, requestedName}, locale
            ));
        }
    }

    private TrainerEntity getTrainerByUUID(UUID trainerUUID, Locale locale) {
        return trainerRepository.findById(trainerUUID)
                .orElseThrow(() -> new TrainerNotFoundException(
                        messageSource.getMessage("trainer.notFound", new Object[]{trainerUUID}, locale)
                ));
    }
}
