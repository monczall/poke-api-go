package com.pokeapigo.core.pokemon.controller;

import com.pokeapigo.core.pokemon.Pokemon;
import com.pokeapigo.core.pokemon.PokemonService;
import com.pokeapigo.core.pokemon.dto.request.CreatePokemonRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.*;

@Controller
@RequestMapping(API + API_VERSION_1 + API_POKEMONS)
public class PokemonController {

    private final PokemonService pokemonService;

    PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    ResponseEntity<Pokemon> createPokemon(@Valid @RequestBody CreatePokemonRequest createPokemonRequest) {
        final Pokemon result = pokemonService.createPokemon(createPokemonRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
}
