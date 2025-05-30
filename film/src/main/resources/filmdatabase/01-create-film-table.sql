CREATE TABLE IF NOT EXISTS film
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    description      TEXT,
    duration_minutes INTEGER,
    release_date     DATE,
    director         VARCHAR(255),
    rating           NUMERIC(3,2),
    country          VARCHAR(255),
    age_restriction  INTEGER,
    trailer_url      VARCHAR(255),
    cover_image      VARCHAR(255),
    title_image      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS actor
(
    id         SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name  VARCHAR(100) NOT NULL,
    birth_date DATE,
    photo      VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS film_actor
(
    film_id  INTEGER NOT NULL,
    actor_id INTEGER NOT NULL,
    PRIMARY KEY (film_id, actor_id),
    FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE,
    FOREIGN KEY (actor_id) REFERENCES actor (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS genre
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  BIGINT NOT NULL,
    genre_id BIGINT NOT NULL,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES film (id) ON DELETE CASCADE,
    FOREIGN KEY (genre_id) REFERENCES genre (id) ON DELETE CASCADE
);
