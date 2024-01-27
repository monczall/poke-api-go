DROP TABLE IF EXISTS TRA_TRAINERS;

CREATE TABLE TRA_TRAINERS (
        id UUID DEFAULT gen_random_uuid () PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        level INTEGER DEFAULT(1) NOT NULL,
        team VARCHAR(16) DEFAULT 'NONE' NOT NULL,
        avatar_url VARCHAR(4096),
        friend_code VARCHAR(32) NOT NULL,
        email VARCHAR(512) NOT NULL,
        password VARCHAR(512) NOT NULL,
        role VARCHAR(32) DEFAULT 'USER' NOT NULL
    );