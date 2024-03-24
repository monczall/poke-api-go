package com.pokeapigo.core.module.pokemon.impl;

import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonRepository;
import com.pokeapigo.core.module.pokemon.PokemonService;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonDeleteResponse;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.exception.PokemonAlreadyExistsException;
import com.pokeapigo.core.module.pokemon.exception.PokemonNotFoundException;
import com.pokeapigo.core.module.pokemon.util.PokemonConstants;
import com.pokeapigo.core.module.pokemon.util.PokemonMapper;
import com.pokeapigo.core.module.pokemon.util.PokemonUtils;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.pokeapigo.core.common.util.PokeApiUtils.*;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;
    private final Validator validator;
    private final MessageSource messageSource;
    private final Logger logger = LoggerFactory.getLogger(PokemonServiceImpl.class);

    public PokemonServiceImpl(PokemonRepository pokemonRepository, Validator validator, MessageSource messageSource) {
        this.pokemonRepository = pokemonRepository;
        this.validator = validator;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public PokemonResponse createPokemon(PokemonRequest request, Locale locale) {
        validator.validate(request);
        locale = setEngLocaleIfNull(locale);

        throwIfPokemonAlreadyExists(null, request, locale);

        final PokemonEntity pokemon = PokemonMapper.toEntity(request);
        final PokemonEntity result = pokemonRepository.save(pokemon);

        logger.info("Pokemon {} with ID {}, Name {} and Variant {} has been saved to database",
                pokemon.getId(), pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant());

        return PokemonMapper.toPokemonResponse(result);
    }

    @Override
    public PokemonResponse getPokemon(UUID pokemonUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);

        final PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);

        return PokemonMapper.toPokemonResponse(pokemon);
    }

    @Override
    public List<PokemonResponse> getAllPokemons() {
        logger.warn("Called method to return all pokemons from the database!");

        final List<PokemonEntity> pokemonList = pokemonRepository
                .findAllValidByOrder();

        return pokemonList.stream()
                .map(PokemonMapper::toPokemonResponse)
                .toList();
    }

    @Override
    public Page<PokemonResponse> getPagedPokemons(Pageable pageable, String search, Integer genId, PokemonType typeOne,
                                                  PokemonType typeTwo, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        pageable = ensureMaxPageSize(pageable, PokemonConstants.POKEMON_PAGE_MAX);
        pageable = applyDefaultSortingIfNone(pageable, "pokedexId", "name", "variant");

        final Page<PokemonEntity> pokemonPage = returnPagedPokemons(pageable, search, genId, typeOne, typeTwo, locale);

        return PokemonMapper.toPagedPokemonResponse(pokemonPage);
    }

    @Override
    @Transactional
    public PokemonResponse updatePokemonData(UUID pokemonUUID, PokemonRequest request, Locale locale) {
        validator.validate(request);
        locale = setEngLocaleIfNull(locale);

        throwIfPokemonAlreadyExists(pokemonUUID, request, locale);

        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);
        final PokemonEntity result = pokemonRepository.save(
                PokemonUtils.updatePokemonEntityData(pokemon, request)
        );

        return PokemonMapper.toPokemonResponse(result);
    }

    @Override
    @Transactional
    public void disablePokemon(UUID pokemonUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);
        final String name = pokemon.getName();
        final String variant = pokemon.getVariant();

        logger.info("About to disable Pokemon with UUID: {}, Name: {}, Variant: {}", pokemonUUID, name, variant);
        if (Boolean.FALSE.equals(pokemon.getValidIndicator())) {
            logger.info("Pokemon with UUID: {}, Name: {}, Variant: {} is already disabled. No action taken.",
                    pokemonUUID, name, variant);
            return;
        }
        pokemon.setValidIndicator(false);
        logger.info("Pokemon {} - {} disabled", name, variant);
    }

    @Override
    @Transactional
    public void enablePokemon(UUID pokemonUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);
        final String name = pokemon.getName();
        final String variant = pokemon.getVariant();

        logger.info("About to enable Pokemon with UUID: {}, Name: {}, Variant: {}", pokemonUUID, name, variant);
        if (Boolean.TRUE.equals(pokemon.getValidIndicator())) {
            logger.info("Pokemon with UUID: {}, Name: {}, Variant: {} is already enabled. No action taken.",
                    pokemonUUID, name, variant);
            return;
        }
        pokemon.setValidIndicator(true);
        logger.info("Pokemon {} - {} enabled", name, variant);
    }

    @Override
    @Transactional
    public PokemonDeleteResponse deletePokemon(UUID pokemonUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);

        logger.info("About to delete Pokemon with UUID: {}, ID: {}, Name: {} and Variant: {}",
                pokemonUUID, pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant());
        pokemonRepository.delete(pokemon);
        logger.info("Deletion of Pokemon UUID: {} successful", pokemonUUID);

        final String deleteMessage = messageSource.getMessage(
                "pokemon.deletedSuccess", new Object[]{pokemonUUID}, locale
        );

        return new PokemonDeleteResponse(pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant(), deleteMessage);
    }

    private Page<PokemonEntity> returnPagedPokemons(Pageable pageable, String search, Integer genId, PokemonType typeOne,
                                                    PokemonType typeTwo, Locale locale) {
        try {
            return pokemonRepository.findValidFilteredAndPaged(pageable, search, genId, typeOne, typeTwo);
        } catch (InvalidDataAccessApiUsageException e) {
            throw getCorrectSortingException(e, locale, messageSource);
        }
    }

    private void throwIfPokemonAlreadyExists(UUID pokemonUUID, PokemonRequest pokemonRequest, Locale locale) {
        final boolean pokemonAlreadyExists = pokemonRepository.pokemonExists(
                pokemonUUID,
                pokemonRequest.pokedexId(),
                pokemonRequest.name(),
                pokemonRequest.variant()
        );

        if (pokemonAlreadyExists) {
            throw new PokemonAlreadyExistsException(messageSource.getMessage(
                    "pokemon.alreadyExists", new Object[]{
                            pokemonRequest.pokedexId(),
                            pokemonRequest.name(),
                            pokemonRequest.variant()
                    }, locale));
        }
    }

    private PokemonEntity getPokemonByUUID(UUID pokemonUUID, Locale locale) {
        return pokemonRepository.findById(pokemonUUID)
                .orElseThrow(() -> new PokemonNotFoundException(
                        messageSource.getMessage("pokemon.notFound", new Object[]{pokemonUUID}, locale)
                ));
    }

}
