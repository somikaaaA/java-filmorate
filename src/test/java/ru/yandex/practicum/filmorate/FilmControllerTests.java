package ru.yandex.practicum.filmorate;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootTest
public class FilmControllerTests {
    @Autowired
    private FilmController filmController;

    @Test
    public void filmNameIsBlankTest() {
        Film film = new Film();
        film.setDuration(1);
        film.setName("");
        film.setReleaseDate(LocalDate.of(2008, 1, 1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void descriptionLengthIsMore200Test() {
        Film film = new Film();
        film.setDuration(1);
        film.setName("Mama Mia!");
        film.setDescription("a".repeat(201));
        Assertions.assertThrows(ConstraintViolationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void dateOfFilmIsAfter1895_12_28Test() {
        Film film = new Film();
        film.setDuration(1);
        film.setName("Mama Mia!");
        film.setReleaseDate(LocalDate.of(1891, 1, 1));
        Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
    }

    @Test
    public void filmDurationIsNegativeTest() {
        Film film = new Film();
        film.setName("Mama Mia!");
        film.setDuration(-1);
        film.setReleaseDate(LocalDate.of(2008, 1, 1));
        Assertions.assertThrows(ConstraintViolationException.class, () -> filmController.addFilm(film));
    }
}