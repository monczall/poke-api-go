package com.pokeapigo.core.pokemon.controller;

import com.pokeapigo.core.pokemon.PokemonService;
import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_POKEMONS;
import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_URI_V1;

@Controller
@RequestMapping(API_URI_V1 + API_POKEMONS)
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

    @PutMapping("/{pokemonId}")
    ResponseEntity<PokemonResponse> updatePokemonData(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonId,
            @Valid @RequestBody PokemonRequest request
    ) {
        final PokemonResponse updatedPokemon = pokemonService.updatePokemonData(pokemonId, request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPokemon);
    }

    @PatchMapping("/{pokemonId}/visibility")
    ResponseEntity<PokemonResponse> changePokemonVisibility(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID pokemonId,
            @Valid @RequestBody PokemonVisibilityRequest request
    ) {
        final PokemonResponse updatedPokemon = pokemonService.changePokemonVisibility(pokemonId, request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedPokemon);
    }

    @GetMapping("/secured")
    ResponseEntity<List<PokemonResponse>> getAllPokemons() {
        final List<PokemonResponse> listOfAllPokemons = pokemonService.getAllPokemons();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listOfAllPokemons);
    }

    @GetMapping
    ResponseEntity<Page<PokemonResponse>> getPagedPokemons(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            Pageable pageable,
            @RequestParam(required = false) String name
    ) {
        final Page<PokemonResponse> pagedPokemons = pokemonService.getPagedPokemons(pageable, name, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedPokemons);
    }

}
