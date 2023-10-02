ALTER TABLE pokemons
DROP COLUMN is_wild,
DROP COLUMN is_raid,
DROP COLUMN is_egg,
DROP COLUMN is_shadow,
DROP COLUMN is_mega;

ALTER TABLE pokemons
ADD COLUMN available BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN shiny BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN mega BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN mega_family BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN shadow BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN trade_evolve BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN trade_evolve_family BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN tradeable BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN raidable BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN alternate_form BOOLEAN DEFAULT 'false' NOT NULL,
ADD COLUMN costume_form BOOLEAN DEFAULT 'false' NOT NULL;