package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.mappers.FilmRowMapper;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmDBStorage;
import ru.yandex.practicum.filmorate.dal.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@Import({FilmDBStorage.class, FilmRowMapper.class})
public class FilmDBStorageTests {
    private final FilmStorage filmDBStorage;

    Film film;

    @Autowired
    public FilmDBStorageTests(@Qualifier("FilmDBStorage") FilmDBStorage filmDBStorage) {
        this.filmDBStorage = filmDBStorage;

    }

    @BeforeEach
    public void addTestFilm() {
        film = Film.builder()
                .name("Mama Mia!")
                .description("a".repeat(199))
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
        Film newFilm = filmDBStorage.addFilm(this.film);
        assertThat(newFilm).hasFieldOrPropertyWithValue("id", newFilm.getId());
    }

    @Test
    public void searchFilmByIdTest() {
        Film newFilm = filmDBStorage.addFilm(film);
        Optional<Film> searchFilm = filmDBStorage.getFilmByID(newFilm.getId());
        assertThat(searchFilm)
                .isPresent()
                .hasValueSatisfying(film -> assertThat(film).hasFieldOrPropertyWithValue("id", newFilm.getId()));
    }
}
