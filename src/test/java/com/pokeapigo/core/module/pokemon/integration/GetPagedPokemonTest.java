package com.pokeapigo.core.module.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_POKEMONS;
import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_URI_V1;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

class GetPagedPokemonTest extends TestBaseConfiguration {

    @Nested
    class HappyPaths {

        @ParameterizedTest
        @MethodSource("getSearchParameters")
        void getPagedPokemons_whenGivenDataValid_getPageOfPokemons(String search, Integer genId, String typeOne,
                                                                   String typeTwo, Integer page, Integer size,
                                                                   String expectedName, String expectedVariant
        ) {
            given()
                    .contentType(ContentType.JSON)

                    .when()
                    .pathParams(
                            "search", search,
                            "genId", genId,
                            "typeOne", typeOne,
                            "typeTwo", typeTwo,
                            "page", page,
                            "size", size
                    )
                    .get(
                            API_URI_V1 + API_POKEMONS
                            + "?search={search}&genId={genId}&typeOne={typeOne}&typeTwo={typeTwo}&page={page}&size={size}"
                    )

                    .then()
                    .log().ifValidationFails()
                    .statusCode(HttpStatus.OK.value())

                    .body("content[0].name", is(expectedName))
                    .body("content[0].variant", is(expectedVariant));
        }

        private static List<Arguments> getSearchParameters() {
            return List.of(
                Arguments.of("", 0, "", "", 0, 1, null, null)
            );
        }
    }

    @Nested
    class UnhappyPaths {

    }
}
