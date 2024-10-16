package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@SpringBootTest
public class UserControllerTests {
    static UserController userController = new UserController();

    @Test
    public void emailIsBlankTest() {
        User user = new User();
        user.setEmail("rasqq2qq@gmail.com");
        user.setLogin("");
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Логин не может быть пустым", exception.getMessage());
    }

    @Test
    public void nameIsBlankTest() {
        User user = new User();
        user.setEmail("rasqq2qq@gmail.com");
        user.setLogin("rasqq2qq");
        user.setName("");
        user.setBirthday(LocalDate.of(2002, 6, 4));
        userController.addUser(user);
        Assertions.assertEquals(user.getName(), user.getLogin());
    }

    @Test
    public void birthdayIsAfterTodayTest (){
        User user = new User();
        user.setEmail("rasqq2qq@gmail.com");
        user.setLogin("rasqq2qq");
        user.setBirthday(LocalDate.of(20002, 6, 4));
        Exception exception = Assertions.assertThrows(ValidationException.class, () -> userController.addUser(user));
        Assertions.assertEquals("Дата рождения не может быть в будущем", exception.getMessage());
    }
}
