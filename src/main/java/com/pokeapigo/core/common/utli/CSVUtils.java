package com.pokeapigo.core.common.utli;

import com.pokeapigo.core.exception.CSVParserException;
import com.pokeapigo.core.module.pokemon.PokemonEntity;
import com.pokeapigo.core.module.pokemon.util.PokemonMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.pokeapigo.core.common.utli.constants.Constants.CSV_EXPORT_FILE_NAME;

public class CSVUtils {

    private static final Logger logger = LoggerFactory.getLogger(CSVUtils.class);

    public static boolean isFormatCSV(MultipartFile file) {
        return "text/csv".equals(file.getContentType());
    }

    public static List<PokemonEntity> csvToPokemons(
            InputStream inputStream, MessageSource messageSource, Locale locale
    ) {
        try (
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                CSVParser csvParser = new CSVParser(bufferedReader, getPokemonCSVFormat())
        ) {
            List<PokemonEntity> pokemons = new ArrayList<>();
            Iterable<CSVRecord> records = csvParser.getRecords();

            if (!records.iterator().hasNext()) {
                logger.info("Provided empty CSV");
                return pokemons;
            }

            for (CSVRecord csvRecord : records) {
                logger.info("Processing record: {}", csvRecord);
                final PokemonEntity pokemon = PokemonMapper.toEntity(csvRecord);
                pokemons.add(pokemon);
                logger.info("Processing finished!");
            }

            return pokemons;
        } catch (Exception e) {
            throw new CSVParserException(
                    messageSource.getMessage("pokemon.failedToImport", new Object[]{e.getMessage()}, locale)
            );
        }
    }

    public static ByteArrayInputStream pokemonsToCSV(
            List<PokemonEntity> pokemons, MessageSource messageSource, Locale locale
    ) {
        try (
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(outputStream), getPokemonCSVFormat())
        ) {
            if (pokemons.isEmpty()) {
                logger.info("There are no Pokemons in database to be exported to CSV");
                csvPrinter.flush();
                return new ByteArrayInputStream(outputStream.toByteArray());
            }

            for (PokemonEntity pokemon : pokemons) {
                logger.info("Processing Pokemon: {}", pokemon);
                List<String> data = PokemonMapper.toListOfStrings(pokemon);
                csvPrinter.printRecord(data);
                logger.info("Processing finished!");
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(outputStream.toByteArray());
        } catch (Exception e) {
            throw new CSVParserException(
                    messageSource.getMessage("pokemon.failedToExport", new Object[]{e.getMessage()}, locale)
            );
        }

    }

    public static String createFileNameForCSV(String fileName) {
        if (fileName != null && !fileName.isBlank()) {
            return fileName;
        }
        logger.info("Generating name for CSV");
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        logger.info("Generating name for CSV finished!");

        return CSV_EXPORT_FILE_NAME + "_" + dateTime.format(df);
    }

    private static CSVFormat getPokemonCSVFormat() {
        return CSVFormat.Builder.create()
                .setHeader("pokedexId", "generationId", "name", "variant", "pokemonTypeOne", "pokemonTypeTwo",
                        "pokemonRarity", "available", "shiny", "mega", "megaFamily", "shadow", "tradeEvolve",
                        "tradeEvolveFamily", "tradeable", "raidable", "alternateForm", "costumeForm")
                .setSkipHeaderRecord(true)
                .setDelimiter(';')
                .build();
    }
}
