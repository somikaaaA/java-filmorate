package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode
public class User {
    private long id;
    @Email
    private String email;
    @NotBlank
    private String login;
    @NotBlank
    private String name;
    @PastOrPresent
    @NotNull
    private LocalDate birthday;
    private Set<Long> friends;
}