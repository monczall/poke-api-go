package com.pokeapigo.core.module.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonJsonFactory;
import io.restassured.http.ContentType;
import org.hamcrest.Matcher;
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

class CreatePokemonTest extends TestBaseConfiguration {

    @ParameterizedTest
    @Sql("classpath:sql/cleanUp.sql")
    @MethodSource("pokemonJsonProviderHappyPath")
    void createPokemon_withValidData_thenSavePokemonEntity(String json,
                                                           Matcher<Object> matcher,
                                                           boolean availableVal) {
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
                .body("availability.available", is(availableVal))
                .body("availability.shiny", is(availableVal))
                .body("availability.mega", is(availableVal))
                .body("availability.megaFamily", is(availableVal))
                .body("availability.shadow", is(availableVal))
                .body("availability.tradeEvolve", is(availableVal))
                .body("availability.tradeEvolveFamily", is(availableVal))
                .body("availability.tradeable", is(availableVal))
                .body("availability.raidable", is(availableVal))
                .body("availability.alternateForm", is(availableVal))
                .body("availability.costumeForm", is(availableVal))
                .body("visible", is(true));
    }

    private static List<Arguments> pokemonJsonProviderHappyPath() {
        return List.of(
                Arguments.of(PokemonJsonFactory.CREATE_POKEMON_MINIMAL, nullValue(), false),
                Arguments.of(PokemonJsonFactory.CREATE_POKEMON_MINIMAL_VARIANT, is("Party Hat"), false),
                Arguments.of(PokemonJsonFactory.CREATE_POKEMON_FULL, nullValue(), true),
                Arguments.of(PokemonJsonFactory.CREATE_POKEMON_FULL_VARIANT, is("Party Hat"), true)
        );
    }

    @ParameterizedTest
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/createPokemonInitData.sql"})
    @MethodSource("pokemonJsonProviderUnhappyPath")
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

    private static List<String> pokemonJsonProviderUnhappyPath() {
        return List.of(
                PokemonJsonFactory.CREATE_POKEMON_MINIMAL,
                PokemonJsonFactory.CREATE_POKEMON_MINIMAL_VARIANT,
                PokemonJsonFactory.CREATE_POKEMON_FULL,
                PokemonJsonFactory.CREATE_POKEMON_FULL_VARIANT
        );
    }

}
