package ru.yandex.practicum.filmorate.dal.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User addUser(@RequestBody User user);

    User updateUser(@RequestBody User user);

    List<User> getUsersList();

    Optional<User> getUserById(int id);
}
