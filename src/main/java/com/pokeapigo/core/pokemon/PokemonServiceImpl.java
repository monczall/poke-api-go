package com.pokeapigo.core.pokemon;

import com.pokeapigo.core.exception.exceptions.InvalidColumnNameException;
import com.pokeapigo.core.exception.exceptions.OtherDataAccessApiException;
import com.pokeapigo.core.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.pokemon.exception.exceptions.PokemonAlreadyExistsException;
import com.pokeapigo.core.pokemon.exception.exceptions.PokemonNotFoundException;
import com.pokeapigo.core.pokemon.mapper.PokemonMapper;
import jakarta.validation.Validator;
import org.hibernate.query.SemanticException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
    public PokemonResponse createPokemon(PokemonRequest pokemonRequest, Locale locale) {
        validator.validate(pokemonRequest);

        final boolean pokemonAlreadyExists = pokemonRepository
                .pokemonExists(
                        pokemonRequest.pokedexId(),
                        pokemonRequest.name(),
                        pokemonRequest.variant()
                );

        if (pokemonAlreadyExists) {
            final String message = messageSource.getMessage(
                    "pokemon.alreadyExists", new Object[]{
                            pokemonRequest.pokedexId(),
                            pokemonRequest.name(),
                            pokemonRequest.variant()
                    }, locale);
            throw new PokemonAlreadyExistsException(message);
        }

        final PokemonEntity pokemon = PokemonMapper.toEntity(pokemonRequest);
        final PokemonEntity result = pokemonRepository.save(pokemon);

        logger.info("Pokemon {} with ID {}, Name {} and Variant {} has been saved to database",
                pokemon.getId(), pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant());

        return toPokemonResponse(result);
    }

    @Override
    public List<PokemonResponse> getAllPokemons() {
        logger.warn("Called method to return all pokemons from the database!");

        final List<PokemonEntity> pokemonList = pokemonRepository
                .findAllVisibleOrderByPokedexIdAndName();

        return pokemonList.stream()
                .map(PokemonMapper::toPokemonResponse)
                .toList();
    }

    @Override
    public Page<PokemonResponse> getPagedPokemons(Pageable pageable, String name, Locale locale) {
        try {
            final Page<PokemonEntity> pokemonPage = pokemonRepository
                    .findAllVisibleByNameOrderByPokedexIdAndName(pageable, name);

            return PokemonMapper.toPagedPokemonResponse(pokemonPage);
        } catch (InvalidDataAccessApiUsageException e) {
            if (e.getRootCause() instanceof SemanticException) {
                final String message = messageSource.getMessage(
                        "global.sort.invalidColumnName", null, locale
                );
                throw new InvalidColumnNameException(message);
            }

            final String message = messageSource.getMessage(
                    "global.sort.invalidData", new Object[]{e.getMessage()}, locale
            );
            throw new OtherDataAccessApiException(message, e);
        }
    }

    @Override
    public PokemonResponse changePokemonVisibility(UUID pokemonId, PokemonVisibilityRequest request, Locale locale) {
        validator.validate(request);

        final Boolean requestedVisibility = request.visible();

        PokemonEntity pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException(
                        messageSource.getMessage("pokemon.notFound", new Object[]{pokemonId}, locale)
                ));

        final boolean visibilityChanged = !pokemon.getVisible().equals(requestedVisibility);
        if (visibilityChanged) {
            pokemon.setVisible(requestedVisibility);

            logger.info("Visibility of Pokemon with ID: {} changed to: {}",
                    pokemonId, requestedVisibility);
        }

        return PokemonMapper.toPokemonResponse(pokemonRepository.save(pokemon));
    }

}
