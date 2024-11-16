package ru.yandex.practicum.filmorate.dal.storage.film;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film addFilm(@RequestBody Film film);

    Film updateFilm(@RequestBody Film film);

    List<Film> getAllFilms();

    List<Film> popularFilm(String size);

    Optional<Film> getFilmByID(int id);
}
