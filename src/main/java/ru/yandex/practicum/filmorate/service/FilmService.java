package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final InMemoryFilmStorage filmStorage;
    private final UserStorage userStorage;

    public Film addLike(long userId, long filmId) {
        checkFilmUser(userId, filmId);
        Film film = filmStorage.getFilmById(filmId);
        film.getLike().add(userId);
        log.info("Лайк поставлен.");
        return film;
    }

    public Film deleteLike(long userId, long filmId) {
        checkFilmUser(userId, filmId);
        Film film = filmStorage.getFilmById(filmId);
        film.getLike().remove(userId);
        log.info("Лайк удален.");
        return film;
    }

    public List<Film> popularFilm(Integer limit) {
        return filmStorage.getFilms()
                .stream()
                .sorted(((film1, film2) ->
                        Integer.compare(film2.getLike().size(),
                                film1.getLike().size())))
                .limit(limit)
                .toList();
    }

//    public void checkFilm(long filmId) {
//        if (filmStorage.getFilmById(filmId) == null) {
//            throw new NotFoundException("Фильм с таким id не найден.");
//        }
//    }

    private void checkFilmUser(long userId, long filmId) {
        if (userStorage.getUserById(userId) == null) {
            throw new NotFoundException("Пользователь с таким id не найден.");
        }
        if (filmStorage.getFilmById(filmId) == null) {
            throw new NotFoundException("Фильм с таким id не найден.");
        }
    }
}
