package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreDBStorage extends DBStorage {
    public GenreDBStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    private static final String GENRE_QUERY = "SELECT id, name FROM genre WHERE id = ?";
    private static final String ALL_GENRE_QUERY = "SELECT id, name FROM genre";

    public Optional<Genre> getGenreById(int id) {
        return findOne(GENRE_QUERY, mapper, id);
    }

    public List<Genre> getListGenre(List<Genre> list) {
        String placeholders = String.join(",", Collections.nCopies(list.size(), "?"));
        String listGenreQuery = "SELECT id, name FROM genre WHERE id IN (" + placeholders + ")";
        List<Integer> listInt = list.stream()
                .map(Genre::getId)
                .toList();
        Object[] params = listInt.toArray(new Object[0]);
        return findMany(listGenreQuery, params);
    }

    public List<Genre> getAllGenre() {
        return findMany(ALL_GENRE_QUERY);
    }
}
