package com.pokeapigo.core.module.pokemon.controller.v1;

import com.pokeapigo.core.module.auth.dto.request.ExportCSVRequest;
import com.pokeapigo.core.module.csv.CSVService;
import com.pokeapigo.core.module.pokemon.impl.PokemonCSVServiceImpl;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

import static com.pokeapigo.core.common.util.constants.ApiConstants.*;

@RestController
@RequestMapping(API_URI_V1 + URI_POKEMONS + URI_CSV)
public class PokemonCSVController {

    private CSVService csvService;

    public PokemonCSVController(PokemonCSVServiceImpl csvService) {
        this.csvService = csvService;
    }

    @PostMapping
    ResponseEntity<Boolean> importPokemons(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @RequestBody MultipartFile file
    ) {
        csvService.importCSV(file, locale);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(true);
    }

    @GetMapping
    ResponseEntity<Resource> exportPokemons(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @RequestBody(required = false) ExportCSVRequest request
    ) {
        final InputStreamResource file = csvService.exportCSV(request, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(file);
    }
}
