package com.pokeapigo.core.module.trainer.util;

import com.pokeapigo.core.module.trainer.TrainerRepository;
import com.pokeapigo.core.module.trainer.exception.FailedToGenerateFriendCodeInReasonableAmountOfTries;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.MessageSource;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Locale;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TrainerUtilsTest {

    final TrainerRepository trainerRepository = mock(TrainerRepository.class);

    final MessageSource messageSource = spy(MessageSource.class);

    final Locale defaultLocale = Locale.getDefault();

    TrainerUtils systemUnderTest;

    @BeforeEach
    void setup() {
        systemUnderTest = new TrainerUtils(trainerRepository, messageSource);
    }

    @Test
    void trainerConstantsPrivateConstructorTest() throws NoSuchMethodException {
        Constructor<TrainerConstants> c = TrainerConstants.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(c.getModifiers()));

        c.setAccessible(true);
        Assertions.assertThrows(InvocationTargetException.class, c::newInstance);
    }

    @Test
    void generateFriendCode_whenDataIsCorrect_thenReturnValidFriendCode() {
        // given
        when(trainerRepository.existsByFriendCode(anyString())).thenReturn(false);

        // when
        String generatedFriendCode = systemUnderTest.generateFriendCode(defaultLocale);

        // then
        assertNotNull(generatedFriendCode);
    }

    @Test
    void generateFriendCode_whenNumberOfTriesExceeded_thenThrowException() {
        // given
        when(trainerRepository.existsByFriendCode(anyString())).thenReturn(true);

        // when then
        assertThrows(FailedToGenerateFriendCodeInReasonableAmountOfTries.class, () ->
                systemUnderTest.generateFriendCode(defaultLocale)
        );
    }
}