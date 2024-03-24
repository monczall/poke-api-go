package com.pokeapigo.core.module.pokemon.impl;

import com.pokeapigo.core.common.util.CSVUtils;
import com.pokeapigo.core.exception.CSVParserException;
import com.pokeapigo.core.module.auth.dto.request.ExportCSVRequest;
import com.pokeapigo.core.module.pokemon.PokemonCSVService;
import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonRepository;
import com.pokeapigo.core.module.pokemon.util.PokemonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class PokemonCSVServiceImpl implements PokemonCSVService {

    private static final Logger logger = LoggerFactory.getLogger(PokemonCSVServiceImpl.class);
    private PokemonRepository pokemonRepository;
    private MessageSource messageSource;

    public PokemonCSVServiceImpl(PokemonRepository pokemonRepository, MessageSource messageSource) {
        this.pokemonRepository = pokemonRepository;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public void importPokemonCSV(MultipartFile file, Locale locale) {
        logger.info("Verifying type of file for import");
        if (!CSVUtils.isFormatCSV(file)) {
            throw new CSVParserException("Provided file is not a CSV");
        }

        logger.info("Started import of Pokemons from csv");
        try {
            List<PokemonEntity> pokemons = CSVUtils.csvToPokemons(file.getInputStream(), messageSource, locale);

            pokemons.forEach(pokemon -> {
                PokemonEntity optionalPokemon = pokemonRepository.findByPokedexIdAndNameAndVariant(
                        pokemon.getPokedexId(),
                        pokemon.getName(),
                        pokemon.getVariant()
                ).orElse(null);

                if (null == optionalPokemon) {
                    pokemonRepository.save(pokemon);
                    logger.info("Saved new Pokemon: {}-{}-{}",
                            pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant());
                    return;
                }

                PokemonUtils.updatePokemonEntityData(optionalPokemon, pokemon);
                logger.info("Updated data of existing Pokemon: {}-{}-{}",
                        pokemon.getPokedexId(), pokemon.getName(), pokemon.getVariant());
            });

        } catch (IOException e) {
            throw new CSVParserException(
                    messageSource.getMessage("pokemon.failedToGetCSVFile", new Object[]{e.getMessage()}, locale)
            );
        }
        logger.info("Import from CSV finished!");
    }

    @Override
    public InputStreamResource exportPokemonCSV(ExportCSVRequest request, Locale locale) {
        logger.info("Checking requested Pokemon CSV filename");
        final String fileName = CSVUtils.createFileNameForCSV(
                Optional.ofNullable(request).orElse(new ExportCSVRequest(null)).name()
        );
        logger.info("Started export of Pokemons to CSV file: {}", fileName);

        final List<PokemonEntity> pokemons = pokemonRepository.findAllValidByOrder();
        final ByteArrayInputStream in = CSVUtils.pokemonsToCSV(pokemons, messageSource, locale);

        logger.info("Export of Pokemons to CSV file finished!");
        return new InputStreamResource(in);
    }


}
