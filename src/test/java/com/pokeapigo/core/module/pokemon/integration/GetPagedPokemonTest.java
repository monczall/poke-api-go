package com.pokeapigo.core.module.pokemon.integration;

import com.pokeapigo.core.common.config.TestBaseConfiguration;
import com.pokeapigo.core.module.pokemon.mapper.PokemonMapper;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_POKEMONS;
import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_URI_V1;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Import(PokemonMapper.class)
class GetPagedPokemonTest extends TestBaseConfiguration {

    @ParameterizedTest
    @Sql(scripts = {"classpath:sql/cleanUp.sql", "classpath:sql/pokemon/searchPagedPokemonInitData.sql"})
    @MethodSource("getPagedPokemonsParametersHappyPath")
    @DisplayName("Should return page of Pokemons when searching and sorting data valid")
    void getPagedPokemons_whenGivenDataValid_getPageOfPokemons(String search, Integer genId, String typeOne,
                                                               String typeTwo, Integer page, Integer size, String sort,
                                                               String expectedName, String expectedVariant
    ) {
        given()
                .contentType(ContentType.JSON)

                .when()
                .get(API_URI_V1 + API_POKEMONS +
                                "?search={search}&genId={genId}&typeOne={typeOne}&typeTwo={typeTwo}&page={page}&size={size}&sort={sort}",
                        search,
                        genId,
                        typeOne,
                        typeTwo,
                        page,
                        size,
                        sort
                )

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .body("content[0].name", is(expectedName))
                .body("content[0].variant", is(expectedVariant));
    }

    private static List<Arguments> getPagedPokemonsParametersHappyPath() {
        return List.of(
                Arguments.of("", 0, "", "", 0, 1, "", null, null),
                Arguments.of("", 1, "", "", 0, 1, "name,asc", "Bulbasaur", null),
                Arguments.of("", 1, "", "", 0, 1, "name,desc", "Venusaur", null),
                Arguments.of("Bulbasaur", 1, "GRASS", "POISON", 0, 1, "name,asc", "Bulbasaur", null),
                Arguments.of("Bulbasaur", 1, "GRASS", "POISON", 1, 1, "name,asc", "Bulbasaur", "Party Hat"),
                Arguments.of("Bulbasaur", 2, "GRASS", "POISON", 0, 1, "", null, null),
                Arguments.of("", 1, "PSYCHIC", "", 0, 1, "", "Mew", null),
                Arguments.of("", 2, "GRASS", "", 0, 1, "", "Chikorita", null),
                Arguments.of("", 2, "GRASS", "POISON", 0, 1, "", null, null),
                Arguments.of("Bulbasaur", 2, "PSYCHIC", "POISON", 0, 1, "", null, null),
                Arguments.of("Mythic", 1, "", "", 0, 1, "", "Mew", null),
                Arguments.of("151", 1, "", "", 0, 1, "", "Mew", null)
        );
    }

    @ParameterizedTest
    @MethodSource("getPagedPokemonsParametersUnhappyPath")
    @DisplayName("Should throw exception when invalid sorting data provided")
    void getPagedPokemons_whenSortingFieldInvalid_throwInvalidColumnNameException(String sortValue,
                                                                                  String expectedMessage
    ) {
        JsonPath jsonPath = given()
                .contentType(ContentType.JSON)

                .when()
                .get(API_URI_V1 + API_POKEMONS + "?sort={sort}", sortValue)

                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.BAD_REQUEST.value())

                .extract().response().jsonPath();

        assertInstanceOf(String.class, jsonPath.get("message"));
        String message = jsonPath.getString("message");
        assertTrue(message.startsWith(expectedMessage));
    }

    private static List<Arguments> getPagedPokemonsParametersUnhappyPath() {
        return List.of(
                Arguments.of("badColumn", "Invalid sorting column name provided"),
                Arguments.of("badColumn%26", "Unexpected error during sorting")
        );
    }

}
