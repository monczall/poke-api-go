package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.util.factory.PokemonDtoFactory;
import com.pokeapigo.core.pokemon.util.factory.PokemonEntityFactory;
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

import static com.pokeapigo.core.pokemon.util.PokemonTestConstants.POKEMON_NAME_BULBASAUR;
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
        @MethodSource("correctPokemonRequestProvider")
        @DisplayName("Should create and save Pokemon when correct Pokemon data is given")
        void createPokemon_whenPokemonRequestDataIsCorrect_thenSavePokemon(PokemonRequest pokemonRequest,
                                                                           PokemonEntity pokemon) {
            // given
            when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(pokemon);

            // when
            systemUnderTest.createPokemon(pokemonRequest);

            // then
            verify(pokemonRepository).save(any(PokemonEntity.class));
        }

        @Test
        @DisplayName("Validator should be called when creating Pokemon")
        void createPokemon_whenCreatingPokemon_thenValidatorShouldBeCalled() {
            // given
            final PokemonRequest pokemonRequest = PokemonDtoFactory.validPokemonRequestBulbasaur();
            final PokemonEntity pokemon = PokemonEntityFactory.validPokemonEntityBulbasaur();
            when(pokemonRepository.save(any(PokemonEntity.class))).thenReturn(pokemon);

            // when
            systemUnderTest.createPokemon(pokemonRequest);

            // then
            verify(validator).validate(any(PokemonRequest.class));
        }

        @Test
        @DisplayName("Should call findAllOrderByPokedexIdAscNameAsc repository method when getting all Pokemons")
        void getAllPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
            // given - when
            systemUnderTest.getAllPokemons();

            // then
            verify(pokemonRepository).findAllOrderByPokedexIdAscNameAscAndVisibleTrue();
        }

        @Test
        @DisplayName("Should call findAllByNameOrderByPokedexIdAscNameAsc repository method when getting paged Pokemons")
        void getPagedPokemons_whenGettingPokemons_thenCallCorrectRepositoryMethod() {
            // given
            final PokemonEntity pokemon = PokemonEntityFactory.validPokemonEntityBulbasaur();
            final PageImpl<PokemonEntity> responseFromDb = new PageImpl<>(List.of(pokemon));
            when(pokemonRepository
                    .findAllByNameOrderByPokedexIdAscNameAscAndVisibleTrue(any(Pageable.class), anyString())
            ).thenReturn(responseFromDb);

            // when
            systemUnderTest.getPagedPokemons(Pageable.ofSize(1), POKEMON_NAME_BULBASAUR);

            // then
            verify(pokemonRepository)
                    .findAllByNameOrderByPokedexIdAscNameAscAndVisibleTrue(any(Pageable.class), anyString());
        }

        private static List<Arguments> correctPokemonRequestProvider() {
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
