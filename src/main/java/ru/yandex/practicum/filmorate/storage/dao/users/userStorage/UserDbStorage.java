package ru.yandex.practicum.filmorate.storage.dao.users.userStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.users.UserStorage;

import java.util.List;
import java.util.Optional;

@Component
@Primary
@Qualifier("UserDbStorage")
@Slf4j
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;
    private long generatedId = 1;

    @Autowired
    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User addUser(User user) {
        String sql = "INSERT INTO USERS(email, login, name, birthday)" +
                " VALUES(?,?,?,?)";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday());
        log.info("пользователь добавлен {}", newUser().get());
        return newUser().get();
    }

    @Override
    public void deleteUser(long id) {
        isUser(id);
        String sql = "DELETE FROM USERS WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateUser(User user) {
        isUser(user.getId());
        String sql = "UPDATE USERS SET EMAIL = ?," +
                "login = ?," +
                "name = ?," +
                "birthday = ? WHERE id = ?";

        jdbcTemplate.update(sql,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());
        log.info("пользователь обновлен {}", user.toString());
    }

    @Override
    public Optional<User> getUser(long id) {

        SqlRowSet userRow = jdbcTemplate.queryForRowSet("SELECT* FROM USERS WHERE id = ?", id);

        if (userRow.next()) {
            User user = new User(
                    userRow.getInt("id"),
                    userRow.getString("email"),
                    userRow.getString("login"),
                    userRow.getString("name"),
                    userRow.getDate("birthday").toLocalDate());
            user.setId(userRow.getLong("id"));
            return Optional.of(user);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUserList() {
        String sql = "SELECT* FROM users;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getString("email"),
                            rs.getString("login"),
                            rs.getString("name"),
                            rs.getDate("birthday").toLocalDate());
                    user.setId(rs.getLong("id"));
                    return user;
                }
        );
    }

    @Override
    public void isUser(long userId) {
        String sql = "SELECT ID FROM USERS WHERE ID=?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, userId);
        if (!rowSet.next()) {
            throw new NotExistUserException(String.format("Пользователя с id = %d не существует", userId));
        }
    }

    private Optional<User> newUser() {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("SELECT * FROM USERS u  ORDER BY u.ID DESC LIMIT 1;");
        if (userRow.next()) {
            User user = new User(
                    userRow.getInt("id"),
                    userRow.getString("email"),
                    userRow.getString("login"),
                    userRow.getString("name"),
                    userRow.getDate("birthday").toLocalDate());
            user.setId(userRow.getLong("id"));
            return Optional.of(user);
        } else {
            throw new NotExistUserException("User not found");
        }
    }
}
