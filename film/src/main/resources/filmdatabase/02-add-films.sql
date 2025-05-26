INSERT INTO actor (first_name, last_name, birth_date)
VALUES ('Robert', 'Downey Jr.', '1965-04-04'),
       ('Scarlett', 'Johansson', '1984-11-22'),
       ('Chris', 'Evans', '1981-06-13'),
       ('Jennifer', 'Lawrence', '1990-08-15'),
       ('Leonardo', 'DiCaprio', '1974-11-11'),
       ('Natalie', 'Portman', '1981-06-09'),
       ('Morgan', 'Freeman', '1937-06-01'),
       ('Meryl', 'Streep', '1949-06-22'),
       ('Tom', 'Hanks', '1956-07-09'),
       ('Emma', 'Stone', '1988-11-06'),
       ('Brad', 'Pitt', '1963-12-18'),
       ('Angelina', 'Jolie', '1975-06-04'),
       ('Denzel', 'Washington', '1954-12-28'),
       ('Anne', 'Hathaway', '1982-11-12'),
       ('Hugh', 'Jackman', '1968-10-12'),
       ('Gal', 'Gadot', '1985-04-30'),
       ('Ryan', 'Gosling', '1980-11-12'),
       ('Zendaya', 'Coleman', '1996-09-01'),
       ('Samuel', 'Jackson', '1948-12-21'),
       ('Cate', 'Blanchett', '1969-05-14');

INSERT INTO genre (name)
VALUES ('Action'),
       ('Adventure'),
       ('Comedy'),
       ('Drama'),
       ('Fantasy'),
       ('Horror'),
       ('Romance'),
       ('Sci-Fi'),
       ('Thriller'),
       ('Mystery'),
       ('Animation'),
       ('Crime'),
       ('Documentary'),
       ('Family'),
       ('Musical'),
       ('Biography'),
       ('War'),
       ('Western'),
       ('Sport'),
       ('History');

INSERT INTO film (title, description, duration_minutes, release_date, director, rating, country, age_restriction)
VALUES
    ('Inception', 'A thief who steals corporate secrets through the use of dream-sharing technology.', 148, '2010-07-16', 'Christopher Nolan', 8.8, 'USA', 13),
    ('The Dark Knight', 'Batman battles the Joker in Gotham City.', 152, '2008-07-18', 'Christopher Nolan', 9.0, 'USA', 13),
    ('Forrest Gump', 'Life story of Forrest Gump, a slow-witted but kind-hearted man.', 142, '1994-07-06', 'Robert Zemeckis', 8.8, 'USA', 13),
    ('Titanic', 'A romance unfolds aboard the doomed Titanic.', 195, '1997-12-19', 'James Cameron', 7.8, 'USA', 13),
    ('The Avengers', 'Earth`s mightiest heroes must come together to stop Loki.', 143, '2012-05-04', 'Joss Whedon', 8.0, 'USA', 13),
    ('La La Land', 'A jazz pianist and an aspiring actress fall in love.', 128, '2016-12-09', 'Damien Chazelle', 8.0, 'USA', 13),
    ('Gladiator', 'A Roman general seeks revenge.', 155, '2000-05-05', 'Ridley Scott', 8.5, 'USA', 16),
    ('The Matrix', 'A hacker learns about the true nature of his reality.', 136, '1999-03-31', 'Lana Wachowski', 8.7, 'USA', 16),
    ('Interstellar', 'Explorers travel through a wormhole in space.', 169, '2014-11-07', 'Christopher Nolan', 8.6, 'USA', 13),
    ('The Shawshank Redemption', 'Two imprisoned men bond over years.', 142, '1994-09-23', 'Frank Darabont', 9.3, 'USA', 16),
    ('Black Panther', 'The king of Wakanda faces new challenges.', 134, '2018-02-16', 'Ryan Coogler', 7.3, 'USA', 13),
    ('The Godfather', 'The aging patriarch of an organized crime dynasty transfers control.', 175, '1972-03-24', 'Francis Ford Coppola', 9.2, 'USA', 18),
    ('Joker', 'Origin story of the iconic Batman villain.', 122, '2019-10-04', 'Todd Phillips', 8.4, 'USA', 18),
    ('Frozen', 'A princess with ice powers embarks on a journey.', 102, '2013-11-27', 'Chris Buck', 7.4, 'USA', 0),
    ('The Lion King', 'A lion prince flees his kingdom.', 88, '1994-06-15', 'Roger Allers', 8.5, 'USA', 0),
    ('Spider-Man: No Way Home', 'Spider-Man faces multiverse threats.', 148, '2021-12-17', 'Jon Watts', 8.3, 'USA', 13),
    ('Wonder Woman', 'An Amazonian princess leaves her island.', 141, '2017-06-02', 'Patty Jenkins', 7.4, 'USA', 13),
    ('Saving Private Ryan', 'World War II rescue mission.', 169, '1998-07-24', 'Steven Spielberg', 8.6, 'USA', 16),
    ('Toy Story', 'Toys come to life when humans aren`t around.', 81, '1995-11-22', 'John Lasseter', 8.3, 'USA', 0),
    ('The Social Network', 'Story of Facebook`s founding.', 120, '2010-10-01', 'David Fincher', 7.7, 'USA', 13);

-- Inception (film_id = 1) - актори: Leonardo DiCaprio (5)
INSERT INTO film_actor (film_id, actor_id) VALUES (1, 5);

-- The Dark Knight (film_id = 2) - актори: Christian Bale (немає у списку),
-- щоб приклад, додамо Morgan Freeman (7)
INSERT INTO film_actor (film_id, actor_id) VALUES (2, 7);

-- Forrest Gump (film_id = 3) - Tom Hanks (9)
INSERT INTO film_actor (film_id, actor_id) VALUES (3, 9);

-- Titanic (film_id = 4) - Leonardo DiCaprio (5), Kate Winslet (немає, можна пропустити)
INSERT INTO film_actor (film_id, actor_id) VALUES (4, 5);

-- The Avengers (film_id = 5) - Robert Downey Jr. (1), Scarlett Johansson (2), Chris Evans (3), Samuel Jackson (19)
INSERT INTO film_actor (film_id, actor_id) VALUES (5, 1), (5, 2), (5, 3), (5, 19);

-- La La Land (film_id = 6) - Ryan Gosling (17), Emma Stone (10)
INSERT INTO film_actor (film_id, actor_id) VALUES (6, 17), (6, 10);

-- Gladiator (film_id = 7) - Russell Crowe (немає у списку), Morgan Freeman (7)
INSERT INTO film_actor (film_id, actor_id) VALUES (7, 7);

-- The Matrix (film_id = 8) - Keanu Reeves (немає), Laurence Fishburne (немає)
-- Пропустимо

-- Interstellar (film_id = 9) - Matthew McConaughey (немає), Anne Hathaway (14)
INSERT INTO film_actor (film_id, actor_id) VALUES (9, 14);

-- The Shawshank Redemption (film_id = 10) - Morgan Freeman (7)
INSERT INTO film_actor (film_id, actor_id) VALUES (10, 7);

-- Black Panther (film_id = 11) - Chadwick Boseman (немає), Lupita Nyong'o (немає)
-- Пропустимо

-- The Godfather (film_id = 12) - Marlon Brando (немає), Al Pacino (немає)
-- Пропустимо

-- Joker (film_id = 13) - Joaquin Phoenix (немає)
-- Пропустимо

-- Frozen (film_id = 14) - анімаційний, акторів немає

-- The Lion King (film_id = 15) - анімаційний

-- Spider-Man: No Way Home (film_id = 16) - Tom Holland (немає)
-- Пропустимо

-- Wonder Woman (film_id = 17) - Gal Gadot (16)
INSERT INTO film_actor (film_id, actor_id) VALUES (17, 16);

-- Saving Private Ryan (film_id = 18) - Tom Hanks (9)
INSERT INTO film_actor (film_id, actor_id) VALUES (18, 9);

-- Toy Story (film_id = 19) - анімаційний

-- The Social Network (film_id = 20) - Jesse Eisenberg (немає)
-- Пропустимо

-- Inception (film_id = 1): Action(1), Sci-Fi(8), Thriller(9)
INSERT INTO film_genre (film_id, genre_id) VALUES (1, 1), (1, 8), (1, 9);

-- The Dark Knight (film_id = 2): Action(1), Crime(12), Drama(4)
INSERT INTO film_genre (film_id, genre_id) VALUES (2, 1), (2, 12), (2, 4);

-- Forrest Gump (film_id = 3): Drama(4), Romance(7)
INSERT INTO film_genre (film_id, genre_id) VALUES (3, 4), (3, 7);

-- Titanic (film_id = 4): Drama(4), Romance(7)
INSERT INTO film_genre (film_id, genre_id) VALUES (4, 4), (4, 7);

-- The Avengers (film_id = 5): Action(1), Adventure(2), Sci-Fi(8)
INSERT INTO film_genre (film_id, genre_id) VALUES (5, 1), (5, 2), (5, 8);

-- La La Land (film_id = 6): Comedy(3), Drama(4), Musical(15), Romance(7)
INSERT INTO film_genre (film_id, genre_id) VALUES (6, 3), (6, 4), (6, 15), (6, 7);

-- Gladiator (film_id = 7): Action(1), Adventure(2), Drama(4), History(20), War(17)
INSERT INTO film_genre (film_id, genre_id) VALUES (7, 1), (7, 2), (7, 4), (7, 20), (7, 17);

-- The Matrix (film_id = 8): Action(1), Sci-Fi(8)
INSERT INTO film_genre (film_id, genre_id) VALUES (8, 1), (8, 8);

-- Interstellar (film_id = 9): Adventure(2), Drama(4), Sci-Fi(8)
INSERT INTO film_genre (film_id, genre_id) VALUES (9, 2), (9, 4), (9, 8);

-- The Shawshank Redemption (film_id = 10): Drama(4), Crime(12)
INSERT INTO film_genre (film_id, genre_id) VALUES (10, 4), (10, 12);

-- Black Panther (film_id = 11): Action(1), Adventure(2), Sci-Fi(8)
INSERT INTO film_genre (film_id, genre_id) VALUES (11, 1), (11, 2), (11, 8);

-- The Godfather (film_id = 12): Crime(12), Drama(4)
INSERT INTO film_genre (film_id, genre_id) VALUES (12, 12), (12, 4);

-- Joker (film_id = 13): Crime(12), Drama(4), Thriller(9)
INSERT INTO film_genre (film_id, genre_id) VALUES (13, 12), (13, 4), (13, 9);

-- Frozen (film_id = 14): Animation(11), Family(14), Fantasy(5), Musical(15)
INSERT INTO film_genre (film_id, genre_id) VALUES (14, 11), (14, 14), (14, 5), (14, 15);

-- The Lion King (film_id = 15): Animation(11), Adventure(2), Drama(4), Family(14), Musical(15)
INSERT INTO film_genre (film_id, genre_id) VALUES (15, 11), (15, 2), (15, 4), (15, 14), (15, 15);

-- Spider-Man: No Way Home (film_id = 16): Action(1), Adventure(2), Sci-Fi(8)
INSERT INTO film_genre (film_id, genre_id) VALUES (16, 1), (16, 2), (16, 8);

-- Wonder Woman (film_id = 17): Action(1), Adventure(2), Fantasy(5)
INSERT INTO film_genre (film_id, genre_id) VALUES (17, 1), (17, 2), (17, 5);

-- Saving Private Ryan (film_id = 18): Drama(4), War(17)
INSERT INTO film_genre (film_id, genre_id) VALUES (18, 4), (18, 17);

-- Toy Story (film_id = 19): Animation(11), Adventure(2), Comedy(3), Family(14)
INSERT INTO film_genre (film_id, genre_id) VALUES (19, 11), (19, 2), (19, 3), (19, 14);

-- The Social Network (film_id = 20): Biography(16), Drama(4)
INSERT INTO film_genre (film_id, genre_id) VALUES (20, 16), (20, 4);
