CREATE TABLE IF NOT EXISTS rating (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name varchar(50)
);
CREATE TABLE IF NOT EXISTS status (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name varchar(50)
);
CREATE TABLE IF NOT EXISTS genre (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name varchar(50)
);

CREATE TABLE IF NOT EXISTS films (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar (50) NOT NULL,
	description varchar(200),
	releaseDate date,
	duration integer,
	rating_id INTEGER REFERENCES rating (id)
	);

CREATE TABLE IF NOT EXISTS users (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name varchar(200) NOT NULL,
  email varchar(100),
  login varchar(50) NOT NULL,
  birthday date
  );

CREATE TABLE IF NOT EXISTS friends (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  user_id INTEGER REFERENCES users (id),
  friend_user_id INTEGER REFERENCES users (id),
  status_id INTEGER REFERENCES status (id)
);



CREATE TABLE IF NOT EXISTS likes (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  film_id INTEGER REFERENCES films (id),
  like_user_id INTEGER REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS filmGenre (
  id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  film_id INTEGER REFERENCES films (id),
  genre_id INTEGER REFERENCES genre (id)
);