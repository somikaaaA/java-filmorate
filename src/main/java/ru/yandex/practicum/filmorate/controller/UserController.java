package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private Long id = 0L;
    private final HashMap<Long, User> users = new HashMap<>();

    @GetMapping
    public List<User> getUsers() {
        LOGGER.info("Получить всех пользователей");
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        validateUser(user);
        user.setId(++id);
        users.put(user.getId(), user);
        LOGGER.info("Пользователь создан: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        if (user.getId() != null) {
            if (users.containsKey(user.getId())) {
                validateUser(user);
                users.put(user.getId(), user);
                LOGGER.info("Пользователь обновлен: {}", user);
                return user;
            }
        }
        LOGGER.warn("User id is invalid");
        throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            LOGGER.error("Неверный адрес электронной почты: ", user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            LOGGER.error("Неверный логин: {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            LOGGER.error("Неверная дата рождения: ", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }
}
