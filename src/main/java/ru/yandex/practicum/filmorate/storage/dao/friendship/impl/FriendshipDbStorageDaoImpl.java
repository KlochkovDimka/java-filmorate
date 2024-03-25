package ru.yandex.practicum.filmorate.storage.dao.friendship.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.friendship.FriendshipDbStorageDao;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class FriendshipDbStorageDaoImpl implements FriendshipDbStorageDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FriendshipDbStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> findFriendsUserById(Long userId) {
        isUser(userId);
        String sql = "SELECT u.* " +
                "FROM FRIENDSHIP f " +
                "INNER JOIN USERS u ON f.FRIENDS_ID = u.ID " +
                "WHERE f.USER_ID = ? AND f.STATUS_ID = 1;";

        List<User> userList = jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User(rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate());
            return user;
        }, userId);
        log.info("Список друзей пользователя {}", userList);

        return userList;
    }

    @Override
    public User addInListFriendsById(Long userId, Long friendId, int statusId) {
        String sql = "INSERT INTO FRIENDSHIP(USER_ID, FRIENDS_ID, STATUS_ID) VALUES (?,?,?);";
        log.info("userId={}, friendId={}, statusId{}", userId, friendId, statusId);
        jdbcTemplate.update(sql, userId, friendId, statusId);
        return getFriendByUser(userId).get();
    }

    @Override
    public void deleteOfListFriendsById(Long userId, Long friendId) {
        String sql = "DELETE FROM FRIENDSHIP WHERE user_id = ? AND friends_id = ?;";
        jdbcTemplate.update(sql, userId, friendId);
    }

    @Override
    public List<User> findListCommonFriendsById(Long userId, Long friendId) {
        String sql = "SELECT u.* " +
                "FROM FRIENDSHIP f " +
                "INNER JOIN USERS u ON u.ID = f.FRIENDS_ID " +
                "WHERE F.USER_ID = ? OR  F.USER_ID = ? " +
                "GROUP BY F.FRIENDS_ID " +
                "HAVING COUNT(F.FRIENDS_ID)>1;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            User user = new User(
                    rs.getInt("id"),
                    rs.getString("email"),
                    rs.getString("login"),
                    rs.getString("name"),
                    rs.getDate("birthday").toLocalDate());
            return user;
        }, userId, friendId);
    }

    private Optional<User> getFriendByUser(long userId) {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("SELECT u.* " +
                "FROM USERS u  " +
                "INNER JOIN FRIENDSHIP f ON u.ID = f.FRIENDS_ID " +
                "WHERE f.USER_ID  = ?", userId);
        if (userRow.next()) {
            User user = new User(
                    userRow.getInt("id"),
                    userRow.getString("email"),
                    userRow.getString("login"),
                    userRow.getString("name"),
                    userRow.getDate("birthday").toLocalDate());
            user.setId(userRow.getLong("id"));
            return Optional.of(user);
        }
        throw new NotExistUserException("User not found");
    }

    private void isUser(long userId) {
        String sql = "SELECT ID FROM USERS WHERE ID=?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (!rowSet.next()) {
            throw new NotExistUserException(String.format("Пользователя с id = %d не существует", userId));
        }
    }
}
