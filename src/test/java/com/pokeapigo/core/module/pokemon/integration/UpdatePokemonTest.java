package com.pokeapigo.core.module.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonJsonFactory;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
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

class UpdatePokemonTest extends TestBaseConfiguration {

    @ParameterizedTest
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/updatePokemonInitData.sql"})
    @MethodSource("updatePokemonProviderHappyPath")
    @DisplayName("Should return data of updated Pokemon when given data is valid and able to update")
    void updatePokemonData_whenGivenValidPokemonData_thenReturnUpdatedPokemon(String json,
                                                                              String pokemonUUID,
                                                                              String expectedName,
                                                                              String expectedVariant,
                                                                              boolean availableVal) {
        given()
                .contentType(ContentType.JSON)
                .body(json)

                .when()
                .put(API_URI_V1 + API_POKEMONS + "/{pokemonId}", pokemonUUID)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("pokedexId", is(1))
                .body("generationId", is(1))
                .body("name", is(expectedName))
                .body("variant", is(expectedVariant))
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
                .body("availability.costumeForm", is(availableVal));
    }

    private static List<Arguments> updatePokemonProviderHappyPath() {
        return List.of(
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_NO_VARIANT_IDENTICAL,
                        "c218b692-311b-4d39-ac6b-747921b73474", "Bulbasaur", null, false),
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_NO_VARIANT_CHANGED,
                        "c218b692-311b-4d39-ac6b-747921b73474", "Bulbasaur New", null, false),
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_VARIANT_IDENTICAL,
                        "c218b692-311b-4d39-ac6b-747921b73475", "Bulbasaur", "Party Hat", false),
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_VARIANT_CHANGED,
                        "c218b692-311b-4d39-ac6b-747921b73475", "Bulbasaur", "Party Hat New", false),
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_NAME_AND_VARIANT_CHANGED,
                        "c218b692-311b-4d39-ac6b-747921b73474", "Bulbasaur New", "Party Hat New", false)
        );
    }

    @ParameterizedTest
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/updatePokemonInitData.sql"})
    @MethodSource("updatePokemonProviderUnhappyPath")
    @DisplayName("Should throw exception when updating Pokemon with invalid data or is unable to update")
    void updatePokemonData_whenGivenValidPokemonData_thenReturnUpdatedPokemon(String json,
                                                                              String pokemonUUID) {
        given()
                .contentType(ContentType.JSON)
                .body(json)

                .when()
                .put(API_URI_V1 + API_POKEMONS + "/{pokemonId}", pokemonUUID)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CONFLICT.value())
                .body("statusCode", is(409))
                .body("statusText", is("Conflict"))
                .body("path", is("/api/v1/pokemons/" + pokemonUUID));
    }

    private static List<Arguments> updatePokemonProviderUnhappyPath() {
        return List.of(
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_NO_VARIANT_DUPLICATE,
                        "c218b692-311b-4d39-ac6b-747921b73475"),
                Arguments.of(PokemonJsonFactory.UPDATE_SECOND_POKEMON_NO_VARIANT_DUPLICATE,
                        "c218b692-311b-4d39-ac6b-747921b73474"),
                Arguments.of(PokemonJsonFactory.UPDATE_POKEMON_VARIANT_DUPLICATE,
                        "c218b692-311b-4d39-ac6b-747921b73474")
        );
    }

}
