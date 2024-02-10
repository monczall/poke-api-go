package com.pokeapigo.core.module.pokemon.controller.v1;

import com.pokeapigo.core.module.pokemon.PokemonService;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonDeleteResponse;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.*;

@RestController
@RequestMapping(API_URI_V1 + URI_POKEMONS)
@Tag(name = "Pokemon Controller", description = "Controller used to perform operations on Pokemons")
class PokemonController {

    private final PokemonService pokemonService;

    PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    ResponseEntity<PokemonResponse> createPokemon(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @Valid @RequestBody PokemonRequest pokemonRequest
    ) {
        final PokemonResponse createdPokemon = pokemonService.createPokemon(pokemonRequest, locale);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPokemon);
    }

    @GetMapping("/{pokemonUUID}")
    ResponseEntity<PokemonResponse> getPokemon(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonUUID
    ) {
        final PokemonResponse pokemonResponse = pokemonService.getPokemon(pokemonUUID, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pokemonResponse);
    }

    @GetMapping(URI_SECURED)
    ResponseEntity<List<PokemonResponse>> getAllPokemons() {
        final List<PokemonResponse> listOfAllPokemons = pokemonService.getAllPokemons();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listOfAllPokemons);
    }

    @GetMapping
    ResponseEntity<Page<PokemonResponse>> getPagedPokemons(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Integer genId,
            @RequestParam(required = false) PokemonType typeOne,
            @RequestParam(required = false) PokemonType typeTwo
    ) {
        final Page<PokemonResponse> pagedPokemons = pokemonService.getPagedPokemons(pageable, search, genId, typeOne,
                typeTwo, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedPokemons);
    }

    @PutMapping("/{pokemonUUID}")
    ResponseEntity<PokemonResponse> updatePokemonData(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonUUID,
            @Valid @RequestBody PokemonRequest request
    ) {
        final PokemonResponse updatedPokemon = pokemonService.updatePokemonData(pokemonUUID, request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPokemon);
    }

    @PatchMapping("/{pokemonUUID}/visibility")
    ResponseEntity<PokemonResponse> changePokemonVisibility(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonUUID,
            @Valid @RequestBody PokemonVisibilityRequest request
    ) {
        final PokemonResponse updatedPokemon = pokemonService.changePokemonVisibility(pokemonUUID, request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPokemon);
    }

    @DeleteMapping("/{pokemonUUID}")
    ResponseEntity<PokemonDeleteResponse> deletePokemon(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonUUID
    ) {
        final PokemonDeleteResponse deleteResponse = pokemonService.deletePokemon(pokemonUUID, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(deleteResponse);
    }


}
