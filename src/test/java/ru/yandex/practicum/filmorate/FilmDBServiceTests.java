package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.FilmDBService;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDBServiceTests {
    private final FilmDBService filmDBService;

    Film film;

    @BeforeEach
    public void addTestFilm() {
        film = Film.builder()
                .name("name")
                .description("a".repeat(10))
                .duration(90)
                .releaseDate(LocalDate.of(2008,1,1))
                .mpa(Mpa.builder()
                        .id(1)
                        .build())
                .genres(Arrays.asList(Genre.builder()
                        .id(6)
                        .build()))
                .build();
    }

    @Test
    public void addFilmTest() {
        FilmDto filmCreate = filmDBService.addFilm(film);
        assertThat(filmCreate)
                .hasFieldOrPropertyWithValue("mpa.name", "G");
    }

    @Test
    public void getFilmByIdTest() {
        FilmDto filmCreate = filmDBService.addFilm(film);
        FilmDto filmCreateDB = filmDBService.getFilmById(film.getId());
        assertThat(filmCreateDB)
                .hasFieldOrPropertyWithValue("id", film.getId())
                .hasFieldOrPropertyWithValue("name", "name");
        assertThat(filmCreate.getGenres())
                .extracting(Genre::getName)
                .contains("Боевик");
    }

}

