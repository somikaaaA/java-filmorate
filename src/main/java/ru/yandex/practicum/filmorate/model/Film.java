package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.yandex.practicum.filmorate.validation.DataReleaseValid;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не должно быть пустым.")
    private String name;
    @Size(min = 1, max = 200, message = "Описание не должно содержать более 200 символов.")
    private String description;
    @DataReleaseValid
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность должна быть положительным числом.")
    private Integer duration;
    private int like;
    private Mpa mpa;
    private List<Genre> genres;
}