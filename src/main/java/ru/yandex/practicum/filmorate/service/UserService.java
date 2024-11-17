package ru.yandex.practicum.filmorate.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.BadRequestException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.dal.storage.user.InMemoryUserStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final InMemoryUserStorage userStorage;

    @Getter
    private final Map<Integer, List<User>> friendsList = new HashMap<>();

    public List<User> friendsListById(int id) {
        if (!friendsList.containsKey(id)) {
            friendsList.put(findUser(id).getId(), new ArrayList<>());
        }
        return friendsList.get(findUser(id).getId());
    }

    public User findUser(long idUser) {
        return userStorage.getUsers().values().stream()
                .filter(us -> us.getId() == idUser)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Пользователь с id " + idUser + " не найден"));
    }

    public boolean checkingFriendsList(int idUser, int idFriend) {
        if (friendsList.isEmpty() | !friendsList.containsKey(idUser)) {
            return true;
        }
        return friendsList.get(idUser).stream()
                .map(User::getId)
                .noneMatch(id -> id == idFriend);
    }

    public List<User> addFriend(int idUser, int idFriend) {
        User user = findUser(idUser);
        User friend = findUser(idFriend);
        if (idUser == idFriend) {
            log.error("Пользователь указал одинаковые id");
            throw new ValidationException("Вы не можете указывать одинаковый id для двух пользователей");
        }
        if (!checkingFriendsList(idUser, idFriend)) {
            log.error("Пользователь повторно добавил в друзья пользователя");
            throw new ValidationException("Пользователь " + friend.getName() +
                    " уже есть в списке друзей");
        }
        putFriendInList(idUser, idFriend);
        putFriendInList(idFriend, idUser);
        return friendsList.get(idUser);
    }

    public void putFriendInList(int idUser, int idFriend) {
        List<User> list = friendsList.isEmpty() | !friendsList.containsKey(idUser) ? new ArrayList<>() : friendsList.get(idUser);
        list.add(findUser(idFriend));
        friendsList.put(idUser, list);
    }

    public List<User> listOfCommonFriends(int idUserOne, int idUserTwo) {
        return friendsList.get(idUserOne).stream()
                .filter(user -> friendsList.get(idUserTwo).contains(user))
                .toList();
    }

    public List<User> deleteFriend(int idOne, int idTwo) {
        findUser(idOne);
        findUser(idTwo);
        friendsList.put(idOne, listWithDeletedUser(idOne, idTwo));
        friendsList.put(idTwo, listWithDeletedUser(idTwo, idOne));
        return friendsList.get(idOne);
    }

    public List<User> listWithDeletedUser(int idOne, int idTwo) {
        if (!friendsList.containsKey(idOne) | !friendsList.containsKey(idTwo)) {
            return new ArrayList<>();
        }
        return friendsList.get(idOne).stream()
                .filter(user -> user.getId() != idTwo)
                .toList();
    }

    public User checkForCreate(User user) {
        if (user.getLogin().contains(" ")) {
            log.error("Пользователь ввел логин с пробелом");
            throw new BadRequestException("Поле логин не может содержать пробелы");
        }
        if (isValueNull(user.getName()) || isLineBlank(user.getName())) {
            user.setName(user.getLogin());
        }
        if (!isDateNull(user.getBirthday())) {
            if (user.getBirthday().isAfter(LocalDate.now())) {
                log.error("Пользователь ввел дату в будущем");
                throw new BadRequestException("Дата рождения не может быть в будущем");
            }
        }
        return user;
    }

    public User checkForUpdate(User user) {
        if (isIdNull(user.getId())) {
            log.error("Пользователь не ввел id");
            throw new ValidationException("Id должен быть указан");
        }
        if (userStorage.getUsers().containsKey(user.getId())) {
            return checkForCreate(user);
        }
        log.error("Фильм с id " + user.getId() + " не найден");
        throw new ValidationException("Фильм с id " + user.getId() + " не найден");
    }

    public boolean isIdNull(int id) {
        return id == 0;
    }

    public boolean isDateNull(LocalDate date) {
        return date == null;
    }

    public boolean isLineBlank(String value) {
        return value.isBlank();
    }

    public boolean isValueNull(String value) {
        return value == null;
    }

}

