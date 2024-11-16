package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.model.User;

import ru.yandex.practicum.filmorate.service.UserDBService;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserDBService userDBService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody User user) {
        return userDBService.addUser(user);
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userDBService.getUsersList();
    }

    @PutMapping
    public UserDto updateUser(@Valid @RequestBody User user) {
        return userDBService.updateUser(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public List<UserDto> addFriend(@PathVariable Map<String, String> allParam) {
        return userDBService.addFriend(Integer.parseInt(allParam.get("id")), Integer.parseInt(allParam.get("friendId")));
    }

    @GetMapping("/{id}/friends")
    public List<UserDto> getFriendsIdForUser(@PathVariable int id) {
        return userDBService.friendsListById(id);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<UserDto> deleteFriend(@PathVariable Map<String, String> allParam) {
        return userDBService.removeFriend(Integer.parseInt(allParam.get("id")),
                Integer.parseInt(allParam.get("friendId")));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<UserDto> getCommonFriends(@PathVariable Map<String, String> allParam) {
        return userDBService.commonFriend(Integer.parseInt(allParam.get("id")), Integer.parseInt(allParam.get("otherId")));
    }
}