package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import ru.yandex.practicum.filmorate.dto.UserDto;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserDBService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDBServiceTests {
    private final UserDBService userDBService;
    User user1, user2, failUser;

    @BeforeEach
    public void addUser() {
        user1 = User.builder()
                .name("name 1")
                .email("rasqq2qq@gmail.com")
                .login("login 1")
                .birthday(LocalDate.of(2002, 6,4))
                .build();
        user2 = User.builder()
                .name("name 2")
                .email("rasqq2qq@gmail.com")
                .login("login 2")
                .birthday(LocalDate.of(2000, 9,10))
                .build();
        failUser = User.builder()
                .name("fake name")
                .email("fakeEmail")
                .login("fake login")
                .birthday(LocalDate.of(2025, 5,4))
                .build();
    }

    @Test
    public void addUserTest() {
        UserDto user1 = userDBService.addUser(this.user1);
        assertThat(user1)
                .hasFieldOrPropertyWithValue("name", "name 1")
                .hasFieldOrPropertyWithValue("email", "rasqq2qq@gmail.com");
    }

    @Test
    public void addFriendTest() {
        UserDto user1 = userDBService.addUser(this.user1);
        UserDto user2 = userDBService.addUser(this.user2);
        List<UserDto> list = userDBService.addFriend(user1.getId(), user2.getId());
        assertThat(list)
                .hasSize(1)
                .extracting(UserDto::getId)
                .contains(user2.getId());
        Exception e = assertThrows(ValidationException.class, () -> userDBService.addFriend(user1.getId(), user2.getId()),
                "Метод работает некорректно");
        assertTrue(e.getMessage().contains("Пользователь с id " + user2.getId() + " уже есть в списке друзей"));
    }
}
