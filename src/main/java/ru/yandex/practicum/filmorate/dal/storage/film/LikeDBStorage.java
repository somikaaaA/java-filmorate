package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.Film;

@Repository
public class LikeDBStorage extends DBStorage {
    public LikeDBStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper, Film.class);
    }

    private static final String INSERT_QUERY = "INSERT INTO likes (film_id, like_user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE_QUERY = "DELETE FROM likes WHERE film_id = ? AND like_user_id = ?";

    public void addLikeToFilm(int filmId, int userId) {
        insert(
                INSERT_QUERY,
                filmId,
                userId
        );
    }
    public void deleteLike(int filmId, int userId) {
        update(
                DELETE_LIKE_QUERY,
                filmId,
                userId
        );
    }
}

