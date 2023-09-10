package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.pokemon.exception.exceptions.PokemonAlreadyExistsException;
import com.pokeapigo.core.pokemon.mapper.PokemonMapper;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

import static com.pokeapigo.core.pokemon.mapper.PokemonMapper.toPokemonResponse;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;
    private final Validator validator;
    private final MessageSource messageSource;
    Logger logger = LoggerFactory.getLogger(PokemonServiceImpl.class);

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

        boolean pokemonAlreadyExists = pokemonRepository
                .findByPokedexIdAndNameIgnoreCaseAndVisibleTrue(pokedexId, pokemonName)
                .isPresent();

        if (pokemonAlreadyExists) {
            String message = messageSource.getMessage(
                    "pokemon.alreadyExists", new Object[]{pokedexId, pokemonName}, Locale.getDefault());
            throw new PokemonAlreadyExistsException(message);
        }

        Pokemon pokemon = PokemonMapper.toEntity(pokemonRequest);
        Pokemon result = pokemonRepository.save(pokemon);

        logger.info("Pokemon with ID %s and Name %s has been saved to database"
                .formatted(pokemon.getId(), pokemon.getName()));

        return toPokemonResponse(result);
    }

    @Override
    public List<PokemonResponse> getAllPokemons() {
        logger.info("Called method to return all pokemons from the database!");

        List<Pokemon> pokemonList = pokemonRepository.findAllOrderByPokedexIdAscNameAsc();

        return pokemonList.stream()
                .map(PokemonMapper::toPokemonResponse)
                .toList();
    }
}
