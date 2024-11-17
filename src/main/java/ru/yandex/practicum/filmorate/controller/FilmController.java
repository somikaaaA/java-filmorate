package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.GenreDto;
import ru.yandex.practicum.filmorate.dto.MpaDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmDBService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class FilmController {
    private final FilmDBService filmDBService;

    @PostMapping("/films")
    public FilmDto addFilm(@Valid @RequestBody Film film) {
        return filmDBService.addFilm(film);
    }

    @PutMapping("/films")
    public FilmDto updateFilm(@Valid @RequestBody Film film) {
        return filmDBService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<FilmDto> getAllFilms() {
        return filmDBService.getAllFilms();
    }

    @GetMapping("/films/{id}")
    public FilmDto getFilmById(@PathVariable int id) {
        return filmDBService.getFilmById(id);
    }

    @GetMapping("/films/popular")
    public List<FilmDto> getPopularFilm(@RequestParam(defaultValue = "10") String count) {
        return filmDBService.popularFilm(count);
    }

    @PutMapping("/films/{id}/like/{userId}") //поставить лайк
    public void addLike(@PathVariable Map<String, String> allParam) {
        filmDBService.addLike(Integer.parseInt(allParam.get("id")), Integer.parseInt(allParam.get("userId")));
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Map<String, String> allParam) {
        filmDBService.unlike(Integer.parseInt(allParam.get("id")), Integer.parseInt(allParam.get("userId")));
    }

    @GetMapping("/mpa")
    public List<MpaDto> getAllMpa() {
        return filmDBService.getAllMpa();
    }

    @GetMapping("/mpa/{id}")
    public MpaDto getMpaById(@PathVariable int id) {
        return filmDBService.getMpaById(id);
    }

    @GetMapping("/genres")
    public List<GenreDto> getAllGenre() {
        return filmDBService.getAllGenre();
    }

    @GetMapping("/genres/{id}")
    public GenreDto getGenreById(@PathVariable int id) {
        return filmDBService.getGenreById(id);
    }
}

