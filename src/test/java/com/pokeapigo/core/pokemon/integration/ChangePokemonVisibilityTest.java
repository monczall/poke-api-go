package com.pokeapigo.core.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.pokemon.util.factory.PokemonJsonFactory;
import io.restassured.http.ContentType;
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

public class ChangePokemonVisibilityTest extends TestBaseConfiguration {

    @ParameterizedTest
    @MethodSource("changePokemonVisibilityProvider")
    @Sql({"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/changeVisibilityInitData.sql"})
    void changePokemonVisibility_whenVisibilityProvided_thenChangeStatus(String changeVisibilityJson,
                                                                         String pokemonUUID,
                                                                         boolean expectedVisibility) {
        given()
                .contentType(ContentType.JSON)
                .body(changeVisibilityJson)

                .when()
                .patch(API_URI_V1 + API_POKEMONS + "/{$pokemonId}/visibility", pokemonUUID)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("name", is("Bulbasaur"))
                .body("visible", is(expectedVisibility));
    }

    private static List<Arguments> changePokemonVisibilityProvider() {
        return List.of(
                Arguments.of(
                        PokemonJsonFactory.CHANGE_VISIBILITY_TRUE,
                        "c218b692-311b-4d39-ac6b-747921b73474",
                        true
                ),
                Arguments.of(
                        PokemonJsonFactory.CHANGE_VISIBILITY_FALSE,
                        "c218b692-311b-4d39-ac6b-747921b73474",
                        false
                ),
                Arguments.of(
                        PokemonJsonFactory.CHANGE_VISIBILITY_TRUE,
                        "c218b692-311b-4d39-ac6b-747921b73475",
                        true
                ),
                Arguments.of(
                        PokemonJsonFactory.CHANGE_VISIBILITY_FALSE,
                        "c218b692-311b-4d39-ac6b-747921b73475",
                        false
                )
        );
    }

}
