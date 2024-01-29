package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.exception.PokemonAlreadyExistsException;
import com.pokeapigo.core.module.pokemon.exception.PokemonNotFoundException;
import com.pokeapigo.core.module.pokemon.impl.PokemonServiceImpl;
import com.pokeapigo.core.module.pokemon.util.PokemonTestConstants;
import com.pokeapigo.core.module.pokemon.util.PokemonUtils;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonDtoFactory;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonEntityFactory;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    final PokemonRepository pokemonRepository = mock(PokemonRepository.class);

    final Validator validator = spy(Validator.class);

    final MessageSource messageSource = spy(MessageSource.class);

    final Locale defaultLocale = Locale.getDefault();

    PokemonService systemUnderTest;

    @BeforeEach
    void setup() {
        systemUnderTest = new PokemonServiceImpl(pokemonRepository, validator, messageSource);
    }

    @ParameterizedTest
    @MethodSource("createPokemonProvider")
    @DisplayName("Should create and save Pokemon when correct Pokemon data is given")
    void createPokemon_whenPokemonDataIsCorrect_thenSavePokemon(PokemonRequest pokemonRequest,
                                                                PokemonEntity pokemon) {
        // given
        when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(pokemon);
        when(pokemonRepository.pokemonExists(any(UUID.class), anyInt(), anyString(), anyString()))
                .thenReturn(false);

        // when
        systemUnderTest.createPokemon(pokemonRequest, defaultLocale);

        // then
        verify(validator).validate(any(PokemonRequest.class));
        verify(pokemonRepository).save(any(PokemonEntity.class));
    }

    @ParameterizedTest
    @MethodSource("updatePokemonProvider")
    @DisplayName("Should update Pokemon when correct Pokemon data is given")
    void updatePokemon_whenPokemonDataIsCorrect_thenUpdatePokemon(PokemonEntity findPokemon,
                                                                  PokemonRequest request) {
        // given
        when(pokemonRepository.pokemonExists(any(UUID.class), anyInt(), anyString(), anyString()))
                .thenReturn(false);
        when(pokemonRepository.findById(any(UUID.class))).thenReturn(Optional.ofNullable(findPokemon));
        assert findPokemon != null;
        PokemonEntity pokemonToSave = PokemonUtils.updatePokemonEntityData(findPokemon, request);
        when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(pokemonToSave);

        // when
        PokemonResponse response = systemUnderTest.updatePokemonData(
                PokemonTestConstants.POKEMON_BULBASAUR_UUID,
                request,
                defaultLocale);

        // then
        verify(pokemonRepository).save(any(PokemonEntity.class));
        assertEquals(request.name(), response.name());
        assertEquals(request.variant(), response.variant());
    }

    private static List<Arguments> updatePokemonProvider() {
        return List.of(
                Arguments.of(
                        PokemonEntityFactory.validPokemonEntityBulbasaur(),
                        PokemonDtoFactory.validPokemonRequestVenusaur()
                ),
                Arguments.of(
                        PokemonEntityFactory.validPokemonEntityBulbasaur(),
                        PokemonDtoFactory.validPokemonRequestBulbasaurPartyHat()
                ),
                Arguments.of(
                        PokemonEntityFactory.validPokemonEntityVenusaur(),
                        PokemonDtoFactory.validPokemonRequestIvysaur()
                ),
                Arguments.of(
                        PokemonEntityFactory.validPokemonEntityBulbasaur(),
                        PokemonDtoFactory.validPokemonRequestBulbasaur()
                )
        );
    }

    @Test
    @DisplayName("Should call findAllOrderByPokedexIdAscNameAsc repository method when getting all Pokemons")
    void getAllPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
        // given - when
        systemUnderTest.getAllPokemons();

        // then
        verify(pokemonRepository).findAllVisibleByOrder();
    }

    @Test
    @DisplayName("Should call findAllByNameOrderByPokedexIdAscNameAsc repository method when getting paged Pokemons")
    void getPagedPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
        // given
        final PokemonEntity pokemon = PokemonEntityFactory.validPokemonEntityBulbasaur();
        final PageImpl<PokemonEntity> responseFromDb = new PageImpl<>(List.of(pokemon));
        when(pokemonRepository.findVisibleFilteredAndPaged(
                any(Pageable.class), anyString(), anyInt(), any(PokemonType.class), any(PokemonType.class)
        )).thenReturn(responseFromDb);

        // when
        systemUnderTest.getPagedPokemons(
                Pageable.ofSize(1),
                PokemonTestConstants.POKEMON_NAME_BULBASAUR,
                1,
                PokemonType.GRASS,
                PokemonType.POISON,
                defaultLocale
        );

        // then
        verify(pokemonRepository).findVisibleFilteredAndPaged(
                any(Pageable.class), anyString(), anyInt(), any(PokemonType.class), any(PokemonType.class)
        );
    }

    private static List<Arguments> createPokemonProvider() {
        return List.of(
                Arguments.of(
                        PokemonDtoFactory.validPokemonRequestBulbasaur(),
                        PokemonEntityFactory.validPokemonEntityBulbasaur()
                ),
                Arguments.of(
                        PokemonDtoFactory.validPokemonRequestBulbasaurPartyHat(),
                        PokemonEntityFactory.validPokemonEntityBulbasaurPartyHat()),
                Arguments.of(
                        PokemonDtoFactory.validPokemonRequestIvysaur(),
                        PokemonEntityFactory.validPokemonEntityIvysaur()),
                Arguments.of(
                        PokemonDtoFactory.validPokemonRequestVenusaur(),
                        PokemonEntityFactory.validPokemonEntityVenusaur())
        );
    }

    @Test
    @DisplayName("Should throw PokemonAlreadyExistsException with correct message when pokemon with given name and id already exists")
    void createPokemon_whenPokemonNameAndIdWithStatusVisibleAlreadyExists_throwPokemonAlreadyExistsException() {
        // given
        final PokemonRequest pokemonRequest = PokemonDtoFactory.validPokemonRequestBulbasaur();
        when(pokemonRepository.pokemonExists(any(), anyInt(), anyString(), any()))
                .thenReturn(true);

        // when - then
        assertThrows(PokemonAlreadyExistsException.class, () ->
                systemUnderTest.createPokemon(pokemonRequest, defaultLocale));
        verify(pokemonRepository, never()).save(any(PokemonEntity.class));
    }

    @Test
    @DisplayName("Should throw PokemonNotFound with correct message when pokemon with given name and id already exists")
    void updatePokemon_whenPokemonDataIsIncorrect_thenThrowException() {
        // given
        final PokemonRequest pokemonRequest = PokemonDtoFactory.validPokemonRequestBulbasaur();
        when(pokemonRepository.pokemonExists(any(UUID.class), anyInt(), anyString(), anyString()))
                .thenReturn(false);
        when(pokemonRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // when - then
        assertThrows(PokemonNotFoundException.class, () ->
                systemUnderTest.updatePokemonData(
                        PokemonTestConstants.NON_EXISTENT_POKEMON_UUID,
                        pokemonRequest,
                        defaultLocale));
    }

}
