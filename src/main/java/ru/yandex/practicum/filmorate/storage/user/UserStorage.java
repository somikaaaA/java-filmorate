package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User addUser(@RequestBody User user);

    User updateUser(@RequestBody User user);

    List<User> getUsers();

    User getUserById(long id);
}
