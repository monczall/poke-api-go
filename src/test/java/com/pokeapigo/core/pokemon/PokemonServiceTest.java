package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.MessageSource;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

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
        @MethodSource("correctPokemonRequestProvider")
        @DisplayName("Should create and save Pokemon when correct Pokemon data is given")
        void createPokemon_whenPokemonRequestDataIsCorrect_thenSavePokemon(PokemonRequest pokemonRequest) {
            // given

            // when

            // then

        }

        @Test
        @DisplayName("Validator should be called when creating Pokemon")
        void createPokemon_whenCreatingPokemon_thenValidatorShouldBeCalled() {
            // given

            // when

            // then

        }

        @Test
        @DisplayName("Should call findAllOrderByPokedexIdAscNameAsc repository method when getting all Pokemons")
        void getAllPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
            // given

            // when

            // then
        }

        @Test
        @DisplayName("Should call findAllByNameOrderByPokedexIdAscNameAsc repository method when getting paged Pokemons")
        void getPagedPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
            // given

            // when

            // then
        }

        private static List<PokemonRequest> correctPokemonRequestProvider() {
            return List.of();
        }
    }

    @Nested
    class UnhappyPaths {
        @Test
        @DisplayName("Should throw PokemonAlreadyExistsException with correct message when visible pokemon with given name and id already exists")
        void createPokemon_whenPokemonNameAndIdWithStatusVisibleExists_throwPokemonAlreadyExistsException() {
            // given

            // when

            // then

        }

        @Test
        @DisplayName("Should throw InvalidColumnNameException when invalid column name provided")
        void getPagedPokemons_whenProvidedInvalidColumnName_throwInvalidColumnNameException() {

        }

        @Test
        @DisplayName("Should throw OtherDataAccessApiException when other unknown exception happens")
        void getPagedPokemons_whenUnknownErrorHappened_throwOtherDataAccessApiException() {

        }
    }
}
