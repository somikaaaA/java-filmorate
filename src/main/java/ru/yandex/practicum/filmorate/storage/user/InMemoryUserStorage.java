package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class InMemoryUserStorage implements UserStorage {
    private Long id = 0L;
    private final HashMap<Long, ru.yandex.practicum.filmorate.model.User> users = new HashMap<>();

    @Override
    public User addUser(@RequestBody User user) {
        validateUser(user);
        user.setId(++id);
        user.setFriends(new HashSet<>());
        users.put(user.getId(), user);
        log.info("Пользователь создан: {}", user);
        return user;
    }

    @Override
    public User updateUser(@RequestBody User user) {
        if (user.getId() != 0) {
            if (users.containsKey(user.getId())) {
                validateUser(user);
                users.put(user.getId(), user);
                log.info("Пользователь обновлен: {}", user);
                return user;
            }
        }
        log.warn("Пользователь не найден");
        throw new NotFoundException("Пользователь с id " + user.getId() + " не найден");
    }

    @Override
    public List<User> getUsers() {
        log.info("Получить всех пользователей");
        return new ArrayList<>(users.values());
    }

    @Override
    public User getUserById(long id) {
        if (!users.containsKey(id)) {
            throw new NotFoundException("Пользователь с таким id не найден  " + id);
        }
        log.info("Пользователь с id {} :", id);
        return users.get(id);
    }

    private void validateUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty() || !user.getEmail().contains("@")) {
            log.error("Неверный адрес электронной почты: ", user.getEmail());
            throw new ValidationException("Электронная почта не может быть пустой");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.error("Неверный логин: {}", user.getLogin());
            throw new ValidationException("Логин не может быть пустым");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getBirthday().isAfter(LocalDate.now())) {
            log.error("Неверный день рождения: ", user.getBirthday());
            throw new ValidationException("Дата рождения не может быть в будущем");
        }
    }

    public List<User> getFriends(long userId) {
        return users.get(userId).getFriends().stream()
                .map(this::getUserById)
                .toList();
    }
}
