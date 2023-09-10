package com.pokeapigo.core.pokemon.controller;

import com.pokeapigo.core.pokemon.PokemonService;
import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.*;

@Controller
@RequestMapping(API + API_VERSION_1 + API_POKEMONS)
public class PokemonController {

    private final PokemonService pokemonService;

    PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @PostMapping
    ResponseEntity<PokemonResponse> createPokemon(@Valid @RequestBody PokemonRequest pokemonRequest) {
        final PokemonResponse createdPokemon = pokemonService.createPokemon(pokemonRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdPokemon);
    }

    @GetMapping("/secured")
    ResponseEntity<List<PokemonResponse>> getAllPokemons() {
        final List<PokemonResponse> listOfAllPokemons = pokemonService.getAllPokemons();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listOfAllPokemons);
    }

}
