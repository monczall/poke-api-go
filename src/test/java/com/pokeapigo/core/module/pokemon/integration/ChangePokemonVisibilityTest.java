package com.pokeapigo.core.module.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.module.pokemon.util.PokemonTestConstants;
import com.pokeapigo.core.module.pokemon.util.factory.PokemonJsonFactory;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.pokeapigo.core.common.util.constants.ApiConstants.API_URI_V1;
import static com.pokeapigo.core.common.util.constants.ApiConstants.URI_POKEMONS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class ChangePokemonVisibilityTest extends TestBaseConfiguration {

    @ParameterizedTest
    @MethodSource("changePokemonVisibilityProviderHappyPath")
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/changeVisibilityInitData.sql"})
    void changePokemonVisibility_whenVisibilityProvided_thenChangeStatus(String changeVisibilityJson,
                                                                         String pokemonUUID,
                                                                         boolean expectedVisibility) {
        given()
                .contentType(ContentType.JSON)
                .body(changeVisibilityJson)

                .when()
                .patch(API_URI_V1 + URI_POKEMONS + "/{$pokemonId}/visibility", pokemonUUID)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("Bulbasaur"))
                .body("visible", is(expectedVisibility));
    }

    private static List<Arguments> changePokemonVisibilityProviderHappyPath() {
        return List.of(
                Arguments.of(PokemonJsonFactory.CHANGE_VISIBILITY_TRUE, "c218b692-311b-4d39-ac6b-747921b73474", true),
                Arguments.of(PokemonJsonFactory.CHANGE_VISIBILITY_FALSE, "c218b692-311b-4d39-ac6b-747921b73474", false),
                Arguments.of(PokemonJsonFactory.CHANGE_VISIBILITY_TRUE, "c218b692-311b-4d39-ac6b-747921b73475", true),
                Arguments.of(PokemonJsonFactory.CHANGE_VISIBILITY_FALSE, "c218b692-311b-4d39-ac6b-747921b73475", false)
        );
    }

    @ParameterizedTest
    @MethodSource("changePokemonVisibilityProviderUnhappyPath")
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/changeVisibilityInitData.sql"})
    void changePokemonVisibility_whenPokemonNotFound_shouldThrowPokemonNotFoundException(String changeVisibilityJson) {
        given()
                .contentType(ContentType.JSON)
                .body(changeVisibilityJson)

                .when()
                .patch(API_URI_V1 + URI_POKEMONS + "/{$pokemonId}/visibility", PokemonTestConstants.NON_EXISTENT_POKEMON_UUID_STRING)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .body("statusCode", is(404))
                .body("statusText", is(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .body("message", Matchers.is("Pokémon with UUID: %s not found".formatted(PokemonTestConstants.NON_EXISTENT_POKEMON_UUID_STRING)));
    }

    private static List<String> changePokemonVisibilityProviderUnhappyPath() {
        return List.of(
                PokemonJsonFactory.CHANGE_VISIBILITY_TRUE,
                PokemonJsonFactory.CHANGE_VISIBILITY_FALSE
        );
    }

}
