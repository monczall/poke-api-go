package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.CreatePokemonRequest;
import org.springframework.stereotype.Service;

@Service
public class PokemonServiceImpl implements PokemonService {

    @Override
    public Pokemon createPokemon(CreatePokemonRequest createPokemonRequest) {
        System.out.println(createPokemonRequest);
        return null;
    }
}
