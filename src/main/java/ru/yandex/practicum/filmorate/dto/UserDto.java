package ru.yandex.practicum.filmorate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
@Builder
public class UserDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int id;
    private String email;
    private String name;
    private String login;
    private LocalDate birthday;


}
