package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmGenreDBStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.GenreDBStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.LikeDBStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.MpaDBStorage;

import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;

import ru.yandex.practicum.filmorate.exception.BadRequestException;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.mapper.GenreMapper;
import ru.yandex.practicum.filmorate.mapper.MpaMapper;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;


import java.util.List;

@Slf4j
@Service
public class FilmDBService {
    private final FilmStorage filmDBStorage;
    private final GenreDBStorage genreDBStorage;
    private final LikeDBStorage likeDBStorage;
    private final UserDBService userDBService;
    private final MpaDBStorage mpaDBStorage;
    private final FilmGenreDBStorage filmGenreDBStorage;

    public FilmDBService(@Qualifier("FilmDBStorage") FilmStorage filmDBStorage, GenreDBStorage genreDBStorage,
                         UserDBService userDBService, LikeDBStorage likeDBStorage,
                         MpaDBStorage mpaDBStorage, FilmGenreDBStorage filmGenreDBStorage) {
        this.filmDBStorage = filmDBStorage;
        this.genreDBStorage = genreDBStorage;
        this.userDBService = userDBService;
        this.likeDBStorage = likeDBStorage;
        this.mpaDBStorage = mpaDBStorage;
        this.filmGenreDBStorage = filmGenreDBStorage;

    }

    public FilmDto addFilm(Film film) {
        setMpa(film);
        setGenre(film);
        filmDBStorage.addFilm(film);
        if (film.getGenres() != null) {
            addToFilmGenreDB(film);
        }
        return FilmMapper.mapToFilmDto(film);
    }

    public FilmDto updateFilm(Film film) {
        checkForUpdate(film);
        setMpa(film);
        setGenre(film);
        filmDBStorage.updateFilm(film);
        return FilmMapper.mapToFilmDto(film);
    }

    public FilmDto getFilmById(int id) {
        if (filmDBStorage.getFilmByID(id).isEmpty()) {
            log.error("Введен неверный id");
            throw new ValidationException("Неверный id фильма");
        }
        Film film = filmDBStorage.getFilmByID(id).get();
        searchAndSetMpa(film);
        searchAndSetGenre(film);
        return FilmMapper.mapToFilmDto((film));
    }

    public List<FilmDto> getAllFilms() {
        return listFilmToListDto(filmDBStorage.getAllFilms());

    }

    public void addLike(int filmId, int userId) {
        userDBService.checkUserId(userId);
        likeDBStorage.addLikeToFilm(filmId, userId);
    }

    public void unlike(int filmId, int userId) {
        userDBService.checkUserId(userId);
        likeDBStorage.deleteLike(filmId, userId);
    }

    public List<FilmDto> popularFilm(String count) {
        return listFilmToListDto(filmDBStorage.popularFilm(count));
    }

    public List<MpaDto> getAllMpa() {
        return mpaDBStorage.getAllMpa().stream()
                .map(MpaMapper::mapToMpaDto)
                .toList();
    }

    public MpaDto getMpaById(int id) {
        if (mpaDBStorage.getMpaById(id).isEmpty()) {
            log.error("Введен неверный id");
            throw new ValidationException("Неверный id mpa");
        }
        return MpaMapper.mapToMpaDto(mpaDBStorage.getMpaById(id).get());
    }

    public List<GenreDto> getAllGenre() {
        return genreDBStorage.getAllGenre().stream()
                .map(GenreMapper::mapToGenreDto)
                .toList();
    }

    public GenreDto getGenreById(int id) {
        if (genreDBStorage.getGenreById(id).isEmpty()) {
            log.error("Введен неверный id");
            throw new ValidationException("Неверный id жанра");
        }
        return GenreMapper.mapToGenreDto(genreDBStorage.getGenreById(id).get());
    }

    public void checkForUpdate(Film film) {
        if (isIdNull(film.getId())) {
            log.error("Пользователь не ввел id");
            throw new ValidationException("Id должен быть указан");
        }
        if (filmDBStorage.getFilmByID(film.getId()).isEmpty()) {

            log.error("Фильм с id " + film.getId() + " не найден");
            throw new ValidationException("Фильм с id " + film.getId() + " не найден");
        }
    }

    public boolean isIdNull(int id) {
        return id == 0;
    }

    public Film setMpa(Film film) {
        if (film.getMpa() != null) {
            int mpaId = film.getMpa().getId();
            if (mpaDBStorage.getMpaById(mpaId).isPresent()) {
                film.setMpa(mpaDBStorage.getMpaById(mpaId).get());
                return film;
            } else {
                log.error("Введен несуществующий mpa");
                throw new BadRequestException("mpa с таким id не существует");
            }
        }
        return film;
    }

    public void searchAndSetMpa(Film film) {
        if (mpaDBStorage.getMpaByFilmId(film.getId()).isPresent()) {
            film.setMpa(mpaDBStorage.getMpaByFilmId(film.getId()).get());
        }
    }

    public void searchAndSetGenre(Film film) {
        film.setGenres(filmGenreDBStorage.getGenreForFilm(film.getId()));
    }

    public Film setGenre(Film film) {
        if (film.getGenres() != null) {
            List<Genre> genres = film.getGenres();
            genres = genreDBStorage.getListGenre(genres);
            if (genres.isEmpty()) {
                log.error("Введен несуществующий жанр");
                throw new BadRequestException("Жанр с таким id не существует");
            }
            film.setGenres(genres);
            return film;
        }
        return film;
    }

    public void addToFilmGenreDB(Film film) {
        filmGenreDBStorage.insertGenreForFilm(film.getId(), film.getGenres());
    }

    public List<FilmDto> listFilmToListDto(List<Film> listFilm) {
        return listFilm
                .stream()
                .map(this::setMpa)
                .map(this::setGenre)
                .map(FilmMapper::mapToFilmDto)
                .toList();
    }
}

