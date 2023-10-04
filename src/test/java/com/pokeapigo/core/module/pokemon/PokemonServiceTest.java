package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.exception.exceptions.PokemonAlreadyExistsException;
import com.pokeapigo.core.module.pokemon.exception.exceptions.PokemonNotFoundException;
import com.pokeapigo.core.module.pokemon.mapper.PokemonMapper;
import com.pokeapigo.core.module.pokemon.util.PokemonTestConstants;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonDtoFactory;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonEntityFactory;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    PokemonService systemUnderTest;

    @BeforeEach
    void setup() {
        systemUnderTest = new PokemonServiceImpl(pokemonRepository, validator, messageSource);
    }

    @Nested
    class HappyPaths {

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
            systemUnderTest.createPokemon(pokemonRequest, Locale.getDefault());

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
            PokemonEntity pokemonToSave = PokemonMapper.updatePokemonEntityData(findPokemon, request);
            when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(pokemonToSave);

            // when
            PokemonResponse response = systemUnderTest.updatePokemonData(
                    PokemonTestConstants.POKEMON_BULBASAUR_UUID,
                    request,
                    Locale.ENGLISH);

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
            when(pokemonRepository
                    .findVisibleFilteredAndPaged(any(Pageable.class), anyString())
            ).thenReturn(responseFromDb);

            // when
            systemUnderTest.getPagedPokemons(Pageable.ofSize(1), PokemonTestConstants.POKEMON_NAME_BULBASAUR,
                    Locale.getDefault());

            // then
            verify(pokemonRepository)
                    .findVisibleFilteredAndPaged(any(Pageable.class), anyString());
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
    }

    @Nested
    class UnhappyPaths {
        @Test
        @DisplayName("Should throw PokemonAlreadyExistsException with correct message when pokemon with given name and id already exists")
        void createPokemon_whenPokemonNameAndIdWithStatusVisibleAlreadyExists_throwPokemonAlreadyExistsException() {
            // given
            final PokemonRequest pokemonRequest = PokemonDtoFactory.validPokemonRequestBulbasaur();
            when(pokemonRepository.pokemonExists(any(), anyInt(), anyString(), any()))
                    .thenReturn(true);

            // when - then
            assertThrows(PokemonAlreadyExistsException.class, () ->
                    systemUnderTest.createPokemon(pokemonRequest, Locale.ENGLISH));
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
                            Locale.ENGLISH));
        }
    }
}
