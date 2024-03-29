package com.pokeapigo.core.module.trainer.impl;

import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.TrainerService;
import com.pokeapigo.core.module.trainer.util.TrainerUtils;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

class TrainerServiceImplTest {

    final TrainerRepository trainerRepository = mock(TrainerRepository.class);
    final TrainerUtils trainerUtils = spy(TrainerUtils.class);
    final Validator validator = spy(Validator.class);
    final MessageSource messageSource = spy(MessageSource.class);

    final Locale defaultLocale = Locale.getDefault();

    TrainerService systemUnderTest;

    @BeforeEach
    void setup() {
        systemUnderTest = new TrainerServiceImpl(trainerRepository, trainerUtils, validator, messageSource);
    }
}