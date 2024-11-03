package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Film {
    private Long id;
    @NotBlank(message = "Название фильма не должно быть пустым.")
    private String name;
    @Size(min = 1, max = 200, message = "Описание не должно содержать более 200 символов.")
    private String description;
    @NotNull
    private LocalDate releaseDate;
    @Min(value = 1, message = "Продолжительность должна быть положительным числом.")
    private Integer duration;
    private final Set<Long> like = new HashSet<>();
}