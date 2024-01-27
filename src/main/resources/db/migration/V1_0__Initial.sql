DROP TABLE IF EXISTS POK_POKEMONS;

CREATE TABLE POK_POKEMONS (
        id UUID DEFAULT gen_random_uuid () PRIMARY KEY,
        pokedex_id INTEGER NOT NULL,
        generation_id INTEGER NOT NULL,
        name VARCHAR(255) NOT NULL,
        variant VARCHAR(255),
        type_one VARCHAR(255) DEFAULT 'NONE' NOT NULL,
        type_two VARCHAR(255) DEFAULT 'NONE' NOT NULL,
        rarity VARCHAR(255) DEFAULT 'STANDARD' NOT NULL,
        is_wild BOOLEAN DEFAULT 'false' NOT NULL,
        is_raid BOOLEAN DEFAULT 'false' NOT NULL,
        is_egg BOOLEAN DEFAULT 'false' NOT NULL,
        is_shadow BOOLEAN DEFAULT 'false' NOT NULL,
        is_mega BOOLEAN DEFAULT 'false' NOT NULL
    );