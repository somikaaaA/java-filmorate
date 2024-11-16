package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Repository
public class FilmGenreDBStorage extends DBStorage {
    public FilmGenreDBStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper, Genre.class);
    }

    private static final String GENRE_FOR_FILM_QUERY =
            "SELECT g.id, g.name FROM filmGenre f JOIN genre g ON f.genre_id=g.id WHERE f.film_id = ?";

    public List<Genre> getGenreForFilm(int id) {
        return findMany(GENRE_FOR_FILM_QUERY, id);
    }

    public void insertGenreForFilm(int idFilm, List<Genre> list) {
        List<Integer> listInt = list.stream()
                .map(Genre::getId)
                .toList();
        StringBuilder valuesBuilder = new StringBuilder();
        for (int idGenre : listInt) {
            if (!valuesBuilder.isEmpty()) {
                valuesBuilder.append(", ");
            }
            valuesBuilder.append("(").append(idFilm).append(", ").append(idGenre).append(")");
        }
        String queryInsertGenre = "INSERT INTO filmGenre (film_id, genre_id) VALUES " + valuesBuilder;
        insertMany(queryInsertGenre);
    }
}
