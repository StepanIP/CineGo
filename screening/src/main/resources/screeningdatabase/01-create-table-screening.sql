CREATE TABLE IF NOT EXISTS cinema
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(500) NOT NULL,
    city    VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS screening
(
    id      BIGSERIAL PRIMARY KEY,
    date    DATE,
    time    TIME,
    film_id BIGINT,
    cinema_id INT NOT NULL,
    FOREIGN KEY (cinema_id) REFERENCES cinema (id)
);