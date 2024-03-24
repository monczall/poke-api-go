package com.pokeapigo.core.module.csv;

import com.pokeapigo.core.module.auth.dto.request.ExportCSVRequest;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public interface CSVService {
    void importCSV(MultipartFile file, Locale locale);

    InputStreamResource exportCSV(ExportCSVRequest request, Locale locale);
}
