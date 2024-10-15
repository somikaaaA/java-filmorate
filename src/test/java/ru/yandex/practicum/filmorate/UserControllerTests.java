package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@SpringBootTest
public class UserControllerTests {
    @Autowired
    private FilmController filmController;

    @Test
    public void filmNameIsBlankTest(){
        Film film = new Film();
        film.setDuration(1);
        film.setName("");
        film.setReleaseDate(LocalDate.of(2008, 1, 1));
        Exception exception = Assertions.assertThrows
                (ValidationException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Название фильма не может быть пустым", exception.getMessage());

    }

    @Test
    public void descriptionLengthIsMore200Test() {
        Film film = new Film();
        film.setDuration(1);
        film.setName("Mama Mia!");
        film.setDescription("Софи собирается замуж и мечтает, чтобы церемония прошла " +
                "по всем правилам. Она хочет пригласить на свадьбу отца, " +
                "чтобы он провёл её к алтарю, но не знает, кто он, так как мать никогда не " +
                "рассказывала о нём. Софи находит дневник матери, в котором та описывает " +
                "отношения с тремя мужчинами. Софи решает отправить приглашения всем троим.");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Описание фильма не может содержать более 200 символов", exception.getMessage());
    }

    @Test
    public void dateOfFilmIsAfter1895_12_28Test() {
        Film film = new Film();
        film.setDuration(1);
        film.setName("Mama Mia!");
        film.setReleaseDate(LocalDate.of(1891, 1, 1));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Дата релиза фильма не может быть раньше 1895.12.28", exception.getMessage());
    }

    @Test
    public void filmDurationIsNegativeTest() {
        Film film = new Film();
        film.setName("Mama Mia!");
        film.setDuration(-1);
        film.setReleaseDate(LocalDate.of(2008, 1, 1));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> filmController.addFilm(film));
        Assertions.assertEquals("Продолжительность фильма должна быть положительным числом", exception.getMessage());
    }
}