package com.pokeapigo.core.module.pokemon.util;

import com.pokeapigo.core.module.pokemon.PokemonAvailability;
import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.PokemonTypeDuo;
import com.pokeapigo.core.module.pokemon.dto.request.PokemonRequest;
import com.pokeapigo.core.module.pokemon.dto.response.PokemonResponse;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonRarity;
import com.pokeapigo.core.module.pokemon.util.enums.PokemonType;
import org.apache.commons.csv.CSVRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public final class PokemonMapper {

    /**
     * Creates PokemonEntity object from provided PokemonRequest object.
     * In case of null returns null
     *
     * @param pokemonRequest source PokemonRequest object
     * @return mapped PokemonEntity object
     */
    public static PokemonEntity toEntity(PokemonRequest pokemonRequest) {
        return Optional.ofNullable(pokemonRequest).map(request -> new PokemonEntity(
                        request.pokedexId(),
                        request.generationId(),
                        request.name(),
                        request.variant(),
                        request.pokemonTypes(),
                        request.rarity(),
                        request.availability(),
                        request.validIndicator()
                )
        ).orElse(null);
    }

    /**
     * Creates PokemonResponse object from provided PokemonEntity object.
     * In case of null returns null
     *
     * @param pokemonEntity source PokemonEntity object
     * @return mapped PokemonResponse object
     */
    public static PokemonResponse toPokemonResponse(PokemonEntity pokemonEntity) {
        return Optional.ofNullable(pokemonEntity).map(pokemon -> new PokemonResponse(
                        pokemon.getId(),
                        pokemon.getPokedexId(),
                        pokemon.getGenerationId(),
                        pokemon.getName(),
                        pokemon.getVariant(),
                        pokemon.getPokemonTypes(),
                        pokemon.getRarity(),
                        pokemon.getAvailability(),
                        pokemon.getValidIndicator()
                )
        ).orElse(null);
    }

    /**
     * Creates paged PokemonResponse objects from provided paged PokemonEntity objects.
     * In case of null returns empty page
     *
     * @param pagedPokemons source paged PokemonEntity objects
     * @return mapped page of PokemonResponse objects
     */
    public static Page<PokemonResponse> toPagedPokemonResponse(Page<PokemonEntity> pagedPokemons) {
        return new PageImpl<>(
                pagedPokemons.getContent().stream().map(pokemon ->
                        new PokemonResponse(
                                pokemon.getId(),
                                pokemon.getPokedexId(),
                                pokemon.getGenerationId(),
                                pokemon.getName(),
                                pokemon.getVariant(),
                                pokemon.getPokemonTypes(),
                                pokemon.getRarity(),
                                pokemon.getAvailability(),
                                pokemon.getValidIndicator())
                ).toList()
        );
    }

    /**
     * Creates PokemonEntity object from provided CSVRecord object.
     * In case of missing PokemonType fields method sets type as NONE.
     *
     * @param csvRecord CSV entry object
     * @return mapped PokemonEntity object
     */
    public static PokemonEntity toEntity(CSVRecord csvRecord) {
        PokemonType typeOne = PokemonType.NONE;
        if (!csvRecord.get("pokemonTypeOne").isBlank()) {
            typeOne = PokemonType.valueOf(csvRecord.get("pokemonTypeOne"));
        }
        PokemonType typeTwo = PokemonType.NONE;
        if (!csvRecord.get("pokemonTypeTwo").isBlank()) {
            typeTwo = PokemonType.valueOf(csvRecord.get("pokemonTypeTwo"));
        }

        PokemonAvailability pokemonAvailability = new PokemonAvailability(
                Boolean.parseBoolean(csvRecord.get("available")),
                Boolean.parseBoolean(csvRecord.get("shiny")),
                Boolean.parseBoolean(csvRecord.get("mega")),
                Boolean.parseBoolean(csvRecord.get("megaFamily")),
                Boolean.parseBoolean(csvRecord.get("shadow")),
                Boolean.parseBoolean(csvRecord.get("tradeEvolve")),
                Boolean.parseBoolean(csvRecord.get("tradeEvolveFamily")),
                Boolean.parseBoolean(csvRecord.get("tradeable")),
                Boolean.parseBoolean(csvRecord.get("raidable")),
                Boolean.parseBoolean(csvRecord.get("alternateForm")),
                Boolean.parseBoolean(csvRecord.get("costumeForm"))
        );

        return new PokemonEntity(
                Integer.parseInt(csvRecord.get("pokedexId")),
                Integer.parseInt(csvRecord.get("generationId")),
                csvRecord.get("name"),
                csvRecord.get("variant"),
                new PokemonTypeDuo(typeOne, typeTwo),
                PokemonRarity.valueOf(csvRecord.get("pokemonRarity")),
                pokemonAvailability,
                Boolean.parseBoolean(csvRecord.get("validIndicator"))
        );
    }

    /**
     * Creates List of String objects from provided PokemonEntity object.
     * Mainly used for CSV Pokemon export
     *
     * @param pokemon source Pokemon object
     * @return mapped list of String objects
     */
    public static List<String> toListOfStrings(PokemonEntity pokemon) {
        return Arrays.asList(
                String.valueOf(pokemon.getPokedexId()),
                String.valueOf(pokemon.getGenerationId()),
                pokemon.getName(),
                pokemon.getVariant(),
                String.valueOf(pokemon.getPokemonTypes().getTypeOne()),
                String.valueOf(pokemon.getPokemonTypes().getTypeTwo()),
                String.valueOf(pokemon.getAvailability().isAvailable()),
                String.valueOf(pokemon.getAvailability().isShiny()),
                String.valueOf(pokemon.getAvailability().isMega()),
                String.valueOf(pokemon.getAvailability().isMegaFamily()),
                String.valueOf(pokemon.getAvailability().isShadow()),
                String.valueOf(pokemon.getAvailability().isTradeEvolve()),
                String.valueOf(pokemon.getAvailability().isTradeEvolveFamily()),
                String.valueOf(pokemon.getAvailability().isTradeable()),
                String.valueOf(pokemon.getAvailability().isRaidable()),
                String.valueOf(pokemon.getAvailability().isAlternateForm()),
                String.valueOf(pokemon.getAvailability().isCostumeForm()),
                String.valueOf(pokemon.getAvailability()),
                String.valueOf(pokemon.getValidIndicator())
        );
    }

    private PokemonMapper() {
    }
}
