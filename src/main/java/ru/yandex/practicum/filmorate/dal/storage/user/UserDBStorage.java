package ru.yandex.practicum.filmorate.dal.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository("UserDBStorage")
public class UserDBStorage extends DBStorage implements UserStorage {
    private static final String FIND_All_QUERY = "SELECT id, name, email, login, birthday FROM users";
    private static final String INSERT_QUERY = "INSERT INTO users (name, email, login, birthday) VALUES (?, ?, ?, ?)";
    private static final String FIND_BY_ID_QUERY = "SELECT id, name, email, login, birthday FROM users WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE users SET name = ?, email = ?, login = ?, birthday = ? WHERE id = ?";

    public UserDBStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper, User.class);
    }

    public List<User> getUsersList() {
        return findMany(FIND_All_QUERY);
    }

    public User addUser(User user) {
        int id = insert(
                INSERT_QUERY,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                java.sql.Date.valueOf(user.getBirthday())
        );
        user.setId(id);
        return user;
    }

    public Optional<User> getUserById(int id) {
        return findOne(FIND_BY_ID_QUERY, mapper, id);
    }

    public User updateUser(User user) {
        update(
                UPDATE_QUERY,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                java.sql.Date.valueOf(user.getBirthday()),
                user.getId()
        );
        return user;
    }
}
