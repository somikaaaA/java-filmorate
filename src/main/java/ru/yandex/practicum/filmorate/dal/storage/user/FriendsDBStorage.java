package ru.yandex.practicum.filmorate.dal.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dal.storage.DBStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class FriendsDBStorage extends DBStorage {
    protected final RowMapper<Integer> mapperInt;

    public FriendsDBStorage(JdbcTemplate jdbc, RowMapper<User> mapper, RowMapper<Integer> mapperInt) {
        super(jdbc, mapper, User.class);
        this.mapperInt = mapperInt;
    }

    private static final String INSERT_FRIEND_QUERY = "INSERT INTO friends (user_id, friend_user_id, status_id) " +
            "VALUES (?,?,(SELECT id FROM status WHERE name = ?))";
    private static final String UPDATE_FRIEND_STATUS_QUERY = "UPDATE friends " +
            "SET status_id = (SELECT id FROM status WHERE name = ?) WHERE user_id = ? AND friend_user_id = ?";
    private static final String FIND_ID_QUERY = "SELECT id FROM friends WHERE user_id = ? AND friend_user_id = ? LIMIT 1";

    private static final String FIND_ALL_FRIEND_FOR_ID_QUERY =
            "SELECT u.id, u.name, u.email, u.login, u.birthday" +
                    " FROM friends f" +
                    " JOIN users u ON f.friend_user_id = u.id" +
                    " WHERE f.user_id = ?";

    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friends WHERE user_id = ? AND friend_user_id = ?";

    private static final String FIND_MUTUAL_FRIEND_QUERY =
            "SELECT u.id, u.name, u.email, u.login, u.birthday" +
                    " FROM friends f" +
                    " JOIN users u ON f.friend_user_id = u.id" +
                    " WHERE f.user_id = ?" +
                    " INTERSECT" +
                    " SELECT u.id, u.name, u.email, u.login, u.birthday" +
                    " FROM friends f" +
                    " JOIN users u ON f.friend_user_id = u.id" +
                    " WHERE f.user_id = ?";

    public void insertFriend(int userId, int friendId, String status) {
        insert(
                INSERT_FRIEND_QUERY,
                userId,
                friendId,
                status
        );
        log.info("Пользователь " + friendId + " добавлен в друзья к пользователю " + userId);
    }

    public void updateFriendStatus(int userId, int friendId, String status) {
        update(
                UPDATE_FRIEND_STATUS_QUERY,
                status,
                userId,
                friendId
        );
    }

    public List<User> getFriendsById(int userId) {
        return findMany(
                FIND_ALL_FRIEND_FOR_ID_QUERY,
                userId
        );
    }

    public Optional<Integer> checkFriendsInDB(int userId, int friendId) {
        return findOne(FIND_ID_QUERY, mapperInt, userId, friendId);
    }

    public void deleteFriend(int userId, int friendId) {
        update(
                DELETE_FRIEND_QUERY,
                userId,
                friendId
        );
        log.info("Пользователь " + friendId + " удален из друзей");
    }

    public List<User> getCommonFriends(int idUserOne, int idUserTwo) {
        return findMany(
                FIND_MUTUAL_FRIEND_QUERY,
                idUserOne,
                idUserTwo
        );
    }
}
