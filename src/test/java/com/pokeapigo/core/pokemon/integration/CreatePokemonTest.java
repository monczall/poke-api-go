package com.pokeapigo.core.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.pokemon.util.factory.PokemonJsonFactory;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_POKEMONS;
import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_URI_V1;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CreatePokemonTest extends TestBaseConfiguration {

    @Nested
    class HappyPaths {

        @ParameterizedTest
        @Sql("classpath:sql/cleanUp.sql")
        @MethodSource("minimalPokemonProvider")
        void createPokemon_withMinimalValidData_thenSavePokemonEntity(String json, Matcher<Object> matcher) {
            given()
                    .contentType(ContentType.JSON)
                    .body(json)

                    .when()
                    .post(API_URI_V1 + API_POKEMONS)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("pokedexId", is(1))
                    .body("generationId", is(1))
                    .body("name", is("Bulbasaur"))
                    .body("variant", matcher)
                    .body("pokemonTypes.typeOne", is("GRASS"))
                    .body("pokemonTypes.typeTwo", is("POISON"))
                    .body("rarity", is("STANDARD"))
                    .body("availability.available", is(false))
                    .body("availability.shiny", is(false))
                    .body("availability.mega", is(false))
                    .body("availability.megaFamily", is(false))
                    .body("availability.shadow", is(false))
                    .body("availability.tradeEvolve", is(false))
                    .body("availability.tradeEvolveFamily", is(false))
                    .body("availability.tradeable", is(false))
                    .body("availability.raidable", is(false))
                    .body("availability.alternateForm", is(false))
                    .body("availability.costumeForm", is(false))
                    .body("visible", is(true));
        }

        @ParameterizedTest
        @Sql("classpath:sql/cleanUp.sql")
        @MethodSource("fullPokemonProvider")
        void createPokemon_withFullValidData_thenSavePokemonEntity(String json, Matcher<Object> matcher) {
            given()
                    .contentType(ContentType.JSON)
                    .body(json)

                    .when()
                    .post(API_URI_V1 + API_POKEMONS)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.CREATED.value())
                    .body("pokedexId", is(1))
                    .body("generationId", is(1))
                    .body("name", is("Bulbasaur"))
                    .body("variant", matcher)
                    .body("pokemonTypes.typeOne", is("GRASS"))
                    .body("pokemonTypes.typeTwo", is("POISON"))
                    .body("rarity", is("STANDARD"))
                    .body("availability.available", is(true))
                    .body("availability.shiny", is(true))
                    .body("availability.mega", is(true))
                    .body("availability.megaFamily", is(true))
                    .body("availability.shadow", is(true))
                    .body("availability.tradeEvolve", is(true))
                    .body("availability.tradeEvolveFamily", is(true))
                    .body("availability.tradeable", is(true))
                    .body("availability.raidable", is(true))
                    .body("availability.alternateForm", is(true))
                    .body("availability.costumeForm", is(true))
                    .body("visible", is(true));
        }

        private static List<Arguments> minimalPokemonProvider() {
            return List.of(
                    Arguments.of(PokemonJsonFactory.CREATE_POKEMON_MINIMAL, nullValue()),
                    Arguments.of(PokemonJsonFactory.CREATE_POKEMON_MINIMAL_VARIANT, is("Party Hat"))
            );
        }

        private static List<Arguments> fullPokemonProvider() {
            return List.of(
                    Arguments.of(PokemonJsonFactory.CREATE_POKEMON_FULL, nullValue()),
                    Arguments.of(PokemonJsonFactory.CREATE_POKEMON_FULL_VARIANT, is("Party Hat"))
            );
        }
    }

    @Nested
    class UnhappyPaths {

        @ParameterizedTest
        @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/createPokemonInitData.sql"})
        @MethodSource("pokemonJsonProvider")
        void createPokemon_whenCreatedPokemonsExist_shouldThrowException(String pokemonJson) {
            given()
                    .contentType(ContentType.JSON)
                    .body(pokemonJson)

                    .when()
                    .post(API_URI_V1 + API_POKEMONS)

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.CONFLICT.value());
        }

        private static List<String> pokemonJsonProvider() {
            return List.of(
                    PokemonJsonFactory.CREATE_POKEMON_MINIMAL,
                    PokemonJsonFactory.CREATE_POKEMON_MINIMAL_VARIANT,
                    PokemonJsonFactory.CREATE_POKEMON_FULL,
                    PokemonJsonFactory.CREATE_POKEMON_FULL_VARIANT
            );
        }
    }


}
