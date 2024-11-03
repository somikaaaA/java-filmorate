package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return inMemoryUserStorage.getUsers();
    }

    @PostMapping
    public User addUser(@RequestBody @Valid User user) {
        return inMemoryUserStorage.addUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        return inMemoryUserStorage.updateUser(user);
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable(value = "userId") long userId) {
        return inMemoryUserStorage.getUserById(userId);
    }

    @PutMapping("/{userId}/friends/{friendId}")
    public User addFriend(@PathVariable(value = "userId") long userId,
                          @PathVariable(value = "friendId") long friendId) {
        return userService.addFriend(userId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable(value = "userId") long userId) {
        return userService.getFriends(userId);
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public User deleteFriend(@PathVariable(value = "userId") long userId,
                             @PathVariable(value = "friendId") long friendId) {
        return userService.removeFriend(userId,friendId);
    }


    @GetMapping("/{userId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable(value = "userId") long userId,
                                       @PathVariable(value = "otherId") long otherId) {
        return userService.commonFriend(userId, otherId);
    }
}