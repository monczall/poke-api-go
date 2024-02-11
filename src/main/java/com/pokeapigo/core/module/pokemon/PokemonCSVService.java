package com.pokeapigo.core.module.pokemon;

import com.pokeapigo.core.module.auth.dto.request.ExportCSVRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public interface PokemonCSVService {
    void importPokemonCSV(MultipartFile file, Locale locale);

    InputStreamResource exportPokemonCSV(ExportCSVRequest request, Locale locale);
}
