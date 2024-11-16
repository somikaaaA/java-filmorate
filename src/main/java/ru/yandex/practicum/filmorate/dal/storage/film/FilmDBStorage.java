package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

@Repository("FilmDBStorage")
public class FilmDBStorage extends DBStorage implements FilmStorage {

    private static final String FIND_All_QUERY =
            "SELECT id, name, description, releaseDate, duration, rating_id FROM films";

    private static final String INSERT_QUERY =
            "INSERT INTO films (name, description, releaseDate, duration, rating_id) " +
                    "VALUES (?, ?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY =
            "SELECT id, name, description, releaseDate, duration, rating_id FROM films WHERE id = ?";

    private static final String UPDATE_QUERY =
            "UPDATE films SET name = ?, description = ?, releaseDate = ?, duration = ?," +
                    " rating_id = ? WHERE id = ?";

    private static final String POPULAR_FILM_QUERY =
            "SELECT f.id, f.name, f.description, f.releaseDate, f.duration, f.rating_id" +
                    " FROM films f" +
                    " JOIN (" +
                    " SELECT film_id, COUNT(like_user_id) AS like_count" +
                    " FROM likes" +
                    " GROUP BY film_id" +
                    " ORDER BY like_count DESC) l ON f.id=l.film_id LIMIT ?";

    public FilmDBStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    public List<Film> getAllFilms() {
        return findMany(FIND_All_QUERY);
    }

    public List<Film> popularFilm(String count) {
        return findMany(POPULAR_FILM_QUERY, Integer.parseInt(count));
    }

    public Film addFilm(Film film) {
        int id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                java.sql.Date.valueOf(film.getReleaseDate()),
                film.getDuration(),
                film.getMpa().getId()
        );
        film.setId(id);
        return film;
    }

    public Optional<Film> getFilmByID(int id) {
        return findOne(FIND_BY_ID_QUERY, mapper, id);
    }

    public Film updateFilm(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );
        return film;
    }
}
