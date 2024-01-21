# PokeAPI GO
 
The goal of this project was to create REST API for Pokémon GO bucket list apps.

## Current State

### General
- [ ] Unit tests
- [ ] Controller tests
- [ ] Integration tests


- [ ] Create Documentation
- [ ] Create Release Pipelines
- [ ] Deploy Somewhere 


### Core Service

#### Pokémon
- [x] Create Pokémon Table
- [ ] Fill DB With Pokemon Data
- [x] Feature: Create Pokemon
- [x] Feature: Update Pokemon Data
- [x] Feature: Update Pokémon Visibility (Soft Delete)
- [x] Feature: Get Single Pokémon Details
- [x] Feature: Get All Pokemons
- [x] Feature: Get All Pokémon Paged
- [x] Feature: Add Filtering to Get All Pokémon Paged
- [x] Feature: Remove Pokémon Data (Hard Delete)
- [ ] Feature: Get Pokémon Statistics Data (Available, Released etc.)
- [ ] Feature: Export All Pokémon Data
- [ ] Feature: Import All Pokémon Data
   
  
#### Trainers
- [x] Create Trainers Table
- [ ] Feature: Register (Create Trainer)
- [ ] Feature: Modify Trainer Data
- [ ] Feature: Modify Trainer Roles
- [ ] Feature: Send Registration Email Confirmation To Message Queue
- [ ] Feature: Get All Trainers Paged (With filters)
- [ ] Feature: Get Single Trainer Data
- [ ] Feature: Remove Trainer Data
     

#### Trainers - Pokémon
- [ ] Create Trainers - Pokémon Relation
- [ ] Feature: Assign Pokémon To Trainer
- [ ] Feature: Modify Assignment Data
- [ ] Feature: Get Single Assignment Data
- [ ] Feature: Get All Assignment Data Paged (With filters)
- [ ] Feature: Get Assignment Statistics Data
- [ ] Feature: Remove Single Assignment Data (Soft Delete)
- [ ] Feature: Export Assignment Data For Single Trainer
- [ ] Feature: Import Assignment Data For Single Trainer


### Mailing service
- [x] Create mailing microservice
- [ ] Feature: Read Email Message From Message Queue
- [ ] Feature: Process And Send Received Email Data

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or above
- Maven

### Installation

Clone the repository

```cmd
git clone https://github.com/monczall/poke-api-go.git
git clone https://github.com/monczall/poke-api-go-mailing.git
```

Navigate to each project directory and Use mvn to create application jar.

```cmd
mvn clean verify
```

Start the server

```cmd
java -jar target/poke-api-go-VERSION.jar
java -jar target/poke-api-go-mailing-VERSION.jar
```

Core Server will run on localhost:8080\
Mailing Server will run on localhost:8081

## API

This file contains information about all the API endpoints that this service provides. 
You can report any discrepancies or missing data here: [Twitter (X) - Monczall](https://twitter.com/Monczall).

## What kind of data can you expect

Application stores data about

### Pokemons

- id - internal identifier for Pokémon entry
- pokedexId - identifier of Pokémon found in PokéDex
- generationId - identifier of Pokémon generation
- name - name of Pokémon
- variant - e.g. costume name, primal, alola etc.
- type
  - typeOne - first type of Pokémon
  - typeTwo - second type of Pokémon
- rarity - e.g. standard, legendary, mythic
- available
  - available - is Pokémon available in wild
  - shiny - is Pokémon available as shiny
  - mega - is Pokémon available as mega
  - megaFamily - is Pokémon a family of mega
  - shadow - is Pokémon available as shadow
  - tradeEvolve - does Pokémon have free evolution after trade
  - tradeEvolveFamily - is Pokémon a family of tradeEvolve
  - tradeable - is Pokémon available for trade
  - raidable - is Pokémon available in raids
  - alternateForm - is Pokémon an alternate form
  - costumeForm - is Pokémon costumed
- visible - if set to false, makes Pokémon entity private (not shown when getting Pokémon, not validated for duplicates)

### Enums

#### PokemonRarity:
  NONE | BUG | DARK | DRAGON | ELECTRIC\
  FAIRY | FIGHTING | FIRE | FLYING | GHOST\
  GRASS | GROUND | ICE | NORMAL | POISON\
  PSYCHIC | ROCK | STEEL | WATER

#### PokemonType:
  STANDARD | LEGENDARY | MYTHIC


## Endpoints

### Creating the data

#### POST api/v1/pokemons - create Pokemon 

Request Body: 
```json
{
  "pokedexId": "integer from 1-1200 - required",
  "generationId": "integer from 1-10 - required",
  "name": "string min len. 3 - required",
  "variant": "string from 1-255 - optional",
  "pokemonTypes" : {
    "typeOne": "Enum - PokemonType - required",
    "typeTwo": "Enum - PokemonType - required"
  },
  "rarity": "Enum - PokemonRarity - required",
  "availability": {
    "available": "boolean - optional",
    "shiny": "boolean - optional",
    "mega": "boolean - optional",
    "megaFamily": "boolean - optional",
    "shadow": "boolean - optional",
    "tradeEvolve": "boolean - optional",
    "tradeEvolveFamily": "boolean - optional",
    "tradeable": "boolean - optional",
    "raidable": "boolean - optional",
    "alternateForm": "boolean - optional",
    "costumeForm": "boolean - optional"
  }
  
}
```

Example response Body:
```json
{
  "pokedexId": 1,
  "generationId": 1,
  "name": "Bulbasaur",
  "variant": null,
  "pokemonTypes": {
    "typeOne": "GRASS",
    "typeTwo": "POISON"
  },
  "rarity": "STANDARD",
  "availability": {
    "available": false,
    "shiny": false,
    "mega": false,
    "megaFamily": false,
    "shadow": false,
    "tradeEvolve": false,
    "tradeEvolveFamily": false,
    "tradeable": false,
    "raidable": false,
    "alternateForm": false,
    "costumeForm": false
  },
  "visible": true
}
```


### Reading the data

#### GET api/v1/pokemons/secured

Example response body
```json
[
  {
    "pokedexId": 1,
    "generationId": 1,
    "name": "Bulbasaur",
    "variant": null,
    "pokemonTypes": {
      "typeOne": "GRASS",
      "typeTwo": "POISON"
    },
    "rarity": "STANDARD",
    "availability": {
      "available": false,
      "shiny": false,
      "mega": false,
      "megaFamily": false,
      "shadow": false,
      "tradeEvolve": false,
      "tradeEvolveFamily": false,
      "tradeable": false,
      "raidable": false,
      "alternateForm": false,
      "costumeForm": false
    },
    "visible": true
  },
  ...MORE POKEMONS
]
```

#### GET api/v1/pokemons

Example response body
```json
{
    "content": [
        {
            "pokedexId": 2,
            "generationId": 1,
            "name": "Ivysaur",
            "variant": null,
            "pokemonTypes": {
                "typeOne": "WATER",
                "typeTwo": "NONE"
            },
            "rarity": "STANDARD",
            "availability": {
              "available": false,
              "shiny": false,
              "mega": false,
              "megaFamily": false,
              "shadow": false,
              "tradeEvolve": false,
              "tradeEvolveFamily": false,
              "tradeable": false,
              "raidable": false,
              "alternateForm": false,
              "costumeForm": false
            },
            "visible": true
        },
        ...MORE POKEMONS
    ],
    "pageable": "INSTANCE",
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 2,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```

### Updating the data

#### PUT api/v1/pokemons/{pokemonUUID}

Example request body
```json
{
    "pokedexId": 2,
    "generationId": 1,
    "name": "Venusaur",
    "pokemonTypes": {
        "typeOne": "GRASS",
        "typeTwo": "POISON"
    },
    "rarity": "STANDARD"
}
```

Example response body
```json
{
    "pokemonId": "18549be1-07f5-4fee-8f8c-5b0b037763ab",
    "pokedexId": 2,
    "generationId": 1,
    "name": "Venusaur",
    "variant": null,
    "pokemonTypes": {
        "typeOne": "GRASS",
        "typeTwo": "POISON"
    },
    "rarity": "STANDARD",
    "availability": {
        "available": false,
        "shiny": false,
        "mega": false,
        "megaFamily": false,
        "shadow": false,
        "tradeEvolve": false,
        "tradeEvolveFamily": false,
        "tradeable": false,
        "raidable": false,
        "alternateForm": false,
        "costumeForm": false
    },
    "visible": true
}
```

#### PATCH api/v1/pokemons/{pokemonUUID}/visibility

Example request body
```json
{
  "visible": true
}
```

Example response body
```json
{
    "pokemonId": "6e2b0cf2-28df-453d-b79f-70eaa663ff4f",
    "pokedexId": 1,
    "generationId": 1,
    "name": "Bulbasaur",
    "variant": "Party Hat",
    "pokemonTypes": {
        "typeOne": "GRASS",
        "typeTwo": "POISON"
    },
    "rarity": "STANDARD",
    "availability": {
        "available": true,
        "shiny": true,
        "mega": true,
        "megaFamily": true,
        "shadow": true,
        "tradeEvolve": true,
        "tradeEvolveFamily": true,
        "tradeable": true,
        "raidable": true,
        "alternateForm": true,
        "costumeForm": true
    },
    "visible": true
}
```

### Deleting the data

#### DELETE api/v1/pokemons/{pokemonUUID}

Example response body
```json
{
  "pokedexId": 1,
  "name": "Bulbasaur",
  "variant": "Party Hat",
  "message": "Pokémon with UUID: 6e2b0cf2-28df-453d-b79f-70eaa663ff4f deleted successfully"
}
```

