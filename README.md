# PokeAPI GO
 
The goal of this project was to create REST API for Pokemon GO bucket list apps.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 17 or above
- Maven

### Installation

Clone the repository

```cmd
git clone https://github.com/monczall/poke-api-go.git
```

Navigate to project directory and Use mvn to create application jar.

```cmd
mvn clean varify
```

Start the server

```cmd
java -jar target/poke-api-go-VERSION.jar
```

Server will run on localhost:8080

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
  - isWild - is Pokémon available in wild
  - isRaid - is Pokémon available in raid
  - isEgg - is Pokémon available in egg
  - isShadow - is Pokémon available as shadow
  - isMega - is Pokémon available as mega
- visible - if set to false, makes Pokémon entity private (not shown when getting Pokémon, not validated for duplicates)

### Enums

#### PokemonRarity:
  NONE | BUG | DARK | DRAGON | ELECTRIC | FAIRY | FIGHTING | FIRE | FLYING | GHOST | GRASS | GROUND | ICE | NORMAL | 
  POISON | PSYCHIC | ROCK | STEEL | WATER

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
    "isWild": "boolean - optional",
    "isRaid": "boolean - optional",
    "isEgg": "boolean - optional",
    "isShadow": "boolean - optional",
    "isMega": "boolean - optional"
  }
  
}
```

Example response Body:
```json
{
  "pokedexId": 1,
  "generationId": 1,
  "name": "Bulbasaur",
  "pokemonTypes": {
    "typeOne": "GRASS",
    "typeTwo": "POISON"
  },
  "rarity": "STANDARD",
  "availability": {
    "isWild": false,
    "isRaid": false,
    "isEgg": false,
    "isShadow": false,
    "isMega": false
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
    "pokemonTypes": {
      "typeOne": "GRASS",
      "typeTwo": "POISON"
    },
    "rarity": "STANDARD",
    "availability": {
      "isWild": false,
      "isRaid": false,
      "isEgg": false,
      "isShadow": false,
      "isMega": false
    },
    "visible": true
  },
  ...MORE POKEMONS
]
```

#### GET GET api/v1/pokemons

Example response body
```json
{
    "content": [
        {
            "pokedexId": 2,
            "generationId": 1,
            "name": "Ivysaur",
            "pokemonTypes": {
                "typeOne": "WATER",
                "typeTwo": "NONE"
            },
            "rarity": "STANDARD",
            "availability": {
                "isWild": false,
                "isRaid": false,
                "isEgg": false,
                "isShadow": false,
                "isMega": false
            },
            "visible": null
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

Here is the list of endpoints allowing to modify existing data in the database.



### Deleting the data

Here is the list of endpoints allowing to remove the data from the database or hide it from clients.


