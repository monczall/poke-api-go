package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.pokemon.exception.exceptions.PokemonAlreadyExistsException;
import jakarta.validation.Validator;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static com.pokeapigo.core.pokemon.mapper.PokemonMapper.toPokemonResponse;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;
    private final Validator validator;
    private final MessageSource messageSource;

    public PokemonServiceImpl(PokemonRepository pokemonRepository, Validator validator, MessageSource messageSource) {
        this.pokemonRepository = pokemonRepository;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    public PokemonResponse createPokemon(PokemonRequest pokemonRequest) {
        validator.validate(pokemonRequest);

        Integer pokedexId = pokemonRequest.pokedexId();
        String pokemonName = pokemonRequest.name();

        Pokemon pokemon = pokemonRepository.findByPokedexIdAndNameIgnoreCase(pokedexId, pokemonName).orElseThrow(() -> {
            String message = messageSource.getMessage("pokemon.alreadyExists", new Object[]{pokedexId, pokemonName}, Locale.getDefault());
            throw new PokemonAlreadyExistsException(message);
        });

        Pokemon result = pokemonRepository.save(pokemon);

        return toPokemonResponse(result);
    }
}
