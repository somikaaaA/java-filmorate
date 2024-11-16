package ru.yandex.practicum.filmorate.dal.storage.film;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {
    @Getter
    private final Map<Integer, Film> films = new HashMap<>();

    private int id = 1;

    public int getNextId() {
        return id++;
    }

    @Override
    public List<Film> getAllFilms() {

        return films.values().stream().toList();
    }

    @Override
    public Film addFilm(@RequestBody Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> popularFilm(String size) {
        int count = size == null ? 10 : Integer.parseInt(size);
        if (count > films.size()) {
            count = films.size();
        } else if (count == 0) {
            count = 10;
        }
        List<Film> popularFilms = new LinkedList<>();
        films.values().stream()
                .sorted(Comparator.comparing(Film::getLike).reversed())
                .limit(count)
                .forEach(popularFilms::add);
        return popularFilms;
    }

    public Optional<Film> getFilmByID(int id) { //нужен для реализации другого интерфейса
        return Optional.empty();
    }
}

