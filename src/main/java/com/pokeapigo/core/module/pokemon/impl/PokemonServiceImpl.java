package com.pokeapigo.core.module.pokemon.impl;

import com.pokeapigo.core.exception.exceptions.InvalidColumnNameException;
import com.pokeapigo.core.exception.exceptions.OtherDataAccessApiException;
import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonRepository;
import com.pokeapigo.core.module.pokemon.PokemonService;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonVisibilityRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonDeleteResponse;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.exception.exceptions.PokemonAlreadyExistsException;
import com.pokeapigo.core.module.pokemon.exception.exceptions.PokemonNotFoundException;
import com.pokeapigo.core.module.pokemon.mapper.PokemonMapper;
import com.pokeapigo.core.module.pokemon.util.PokemonConstants;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import jakarta.validation.Validator;
import org.hibernate.query.SemanticException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

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
    public List<PokemonResponse> getAllPokemons() {
        logger.warn("Called method to return all pokemons from the database!");

        final List<PokemonEntity> pokemonList = pokemonRepository
                .findAllVisibleByOrder();

        return pokemonList.stream()
                .map(PokemonMapper::toPokemonResponse)
                .toList();
    }

    @Override
    public Page<PokemonResponse> getPagedPokemons(Pageable pageable, String search, Integer genId, PokemonType typeOne,
                                                  PokemonType typeTwo, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        pageable = ensureMaxPageSize(pageable);
        pageable = applyDefaultSortingIfNone(pageable);

        Page<PokemonEntity> pokemonPage = returnPagedPokemons(pageable, search, genId, typeOne, typeTwo, locale);

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
                PokemonMapper.updatePokemonEntityData(pokemon, request)
        );

        return PokemonMapper.toPokemonResponse(result);
    }

    @Override
    @Transactional
    public PokemonResponse changePokemonVisibility(UUID pokemonUUID, PokemonVisibilityRequest request, Locale locale) {
        validator.validate(request);
        locale = setEngLocaleIfNull(locale);

        final Boolean requestedVisibility = request.visible();

        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);

        final boolean visibilityChanged = !pokemon.getVisible().equals(requestedVisibility);
        if (visibilityChanged) {
            pokemon.setVisible(requestedVisibility);

            logger.info("Visibility of Pokemon with ID: {} changed to: {}",
                    pokemonUUID, requestedVisibility);
        }

        return PokemonMapper.toPokemonResponse(pokemonRepository.save(pokemon));
    }

    @Override
    @Transactional
    public PokemonDeleteResponse deletePokemon(UUID pokemonUUID, Locale locale) {
        locale = setEngLocaleIfNull(locale);
        PokemonEntity pokemon = getPokemonByUUID(pokemonUUID, locale);

        pokemonRepository.delete(pokemon);

        final String deleteMessage = messageSource.getMessage(
                "pokemon.deletedSuccess", new Object[]{pokemonUUID}, locale
        );

        return new PokemonDeleteResponse(pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant(), deleteMessage);
    }

    private Page<PokemonEntity> returnPagedPokemons(Pageable pageable, String search, Integer genId, PokemonType typeOne,
                                                    PokemonType typeTwo, Locale locale) {
        try {
            return pokemonRepository.findVisibleFilteredAndPaged(pageable, search, genId, typeOne, typeTwo);
        } catch (InvalidDataAccessApiUsageException e) {
            throw getCorrectSortingException(e, locale);
        }
    }

    private RuntimeException getCorrectSortingException(InvalidDataAccessApiUsageException e, Locale locale) {
        if (e.getRootCause() instanceof SemanticException) {
            final String message = messageSource.getMessage(
                    "global.sort.invalidColumnName", null, locale
            );
            return new InvalidColumnNameException(message);
        }

        final String message = messageSource.getMessage(
                "global.sort.invalidData", new Object[]{e.getMessage()}, locale
        );
        return new OtherDataAccessApiException(message, e);
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

    private Locale setEngLocaleIfNull(Locale locale) {
        if (locale == null) {
            return Locale.ENGLISH;
        }
        return locale;
    }

    private Pageable ensureMaxPageSize(Pageable pageable) {
        if (pageable.getPageSize() <= PokemonConstants.POKEMON_PAGE_MAX) {
            return pageable;
        }

        logger.warn("Tried to get page of {} Pokemons", pageable.getPageSize());
        pageable = PageRequest.of(
                pageable.getPageNumber(),
                PokemonConstants.POKEMON_PAGE_MAX,
                pageable.getSortOr(Sort.unsorted())
        );
        logger.info("Ensured max page size");

        return pageable;
    }

    private Pageable applyDefaultSortingIfNone(Pageable pageable) {
        Sort sort = pageable.getSortOr(
                Sort.by(Sort.Direction.ASC, "pokedexId", "name", "variant")
        );

        return PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sort
        );
    }
}
