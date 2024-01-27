package com.pokeapigo.core.module.trainer.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_TRAINERS;
import static com.pokeapigo.core.common.utli.constants.ApiConstants.API_URI_V1;

@RestController
@RequestMapping(API_URI_V1 + API_TRAINERS)
@Tag(name = "Trainer Controller", description = "Controller used to perform operations on Trainers")
public class TrainerController {

}
