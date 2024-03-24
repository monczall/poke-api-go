package com.pokeapigo.core.module.trainer.controller.v1;

import com.pokeapigo.core.module.trainer.TrainerService;
import com.pokeapigo.core.module.trainer.dto.request.TrainerRequest;
import com.pokeapigo.core.module.trainer.dto.response.FullTrainerResponse;
import com.pokeapigo.core.module.trainer.dto.response.LimitedTrainerResponse;
import com.pokeapigo.core.role.util.enums.TrainerRole;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static com.pokeapigo.core.common.util.constants.ApiConstants.*;

@RestController
@RequestMapping(API_URI_V1 + URI_TRAINERS)
@Tag(name = "Trainer Controller", description = "Controller used to perform operations on Trainers")
public class TrainerController {

    private TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping("/{trainerUUID}")
    ResponseEntity<LimitedTrainerResponse> getTrainer(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID
    ) {
        final LimitedTrainerResponse trainerResponse = trainerService.getTrainer(trainerUUID, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(trainerResponse);
    }

    @GetMapping(URI_SECURED)
    ResponseEntity<List<FullTrainerResponse>> getAllTrainers() {
        final List<FullTrainerResponse> listOfAllTrainers = trainerService.getAllTrainers();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(listOfAllTrainers);
    }

    @GetMapping
    ResponseEntity<Page<LimitedTrainerResponse>> getPagedTrainers(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PageableDefault Pageable pageable,
            @RequestParam(required = false) String search
    ) {
        final Page<LimitedTrainerResponse> pagedTrainers = trainerService.getPagedTrainers(pageable, search, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(pagedTrainers);
    }

    @PutMapping("/{trainerUUID}")
    ResponseEntity<FullTrainerResponse> updateTrainerData(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID,
            @Valid @RequestBody TrainerRequest trainerRequest
    ) {
        final FullTrainerResponse updatedTrainer = trainerService.updateTrainerData(trainerUUID,
                trainerRequest, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTrainer);
    }

    @PatchMapping("/{trainerUUID}/roles")
    ResponseEntity<FullTrainerResponse> updateTrainerRoles(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID,
            @RequestBody List<TrainerRole> trainerRoles
    ) {
        final FullTrainerResponse updatedTrainer = trainerService.updateTrainerRoles(trainerUUID,
                trainerRoles, locale);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedTrainer);
    }

    @PatchMapping("/{trainerUUID}/disable")
    ResponseEntity<Boolean> deactivateTrainer(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID
    ) {
        trainerService.disableTrainer(trainerUUID, locale);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @PatchMapping("/{trainerUUID}/enable")
    ResponseEntity<Boolean> activateTrainer(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID
    ) {
        trainerService.enableTrainer(trainerUUID, locale);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @DeleteMapping("/{trainerUUID}")
    ResponseEntity<Boolean> deleteTrainer(
            @RequestHeader(value = HttpHeaders.ACCEPT_LANGUAGE, required = false) Locale locale,
            @PathVariable UUID trainerUUID
    ) {
        trainerService.deleteTrainer(trainerUUID, locale);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
}
