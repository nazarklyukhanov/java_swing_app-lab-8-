DROP TABLE IF EXISTS MusicBand CASCADE;
DROP TABLE IF EXISTS Users CASCADE;

DROP DOMAIN IF EXISTS x CASCADE;
DROP TYPE IF EXISTS genre CASCADE;
CREATE TYPE genre as ENUM ('PROGRESSIVE_ROCK', 'JAZZ', 'BLUES');
CREATE DOMAIN x AS DOUBLE PRECISION CHECK (VALUE <= 845);

create table Users(
  id serial PRIMARY KEY,
  login varchar(70) UNIQUE NOT NULL,
  password varchar(70) NOT NULL
);


-- Создание таблицы для хранения людей
CREATE TABLE MusicBand (
  id SERIAL PRIMARY KEY,
  name varchar(70) NOT NULL,
  owner_id int REFERENCES Users(id),
  cor_x x NOT NULL,
  cor_y DOUBLE PRECISION NOT NULL,
  creation_date DATE NOT NULL,
  numberOfParticipants INT,
  albumsCount INT,
  genre VARCHAR(255),
  sales INT NOT NULL
);