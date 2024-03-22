package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.users.userStorage.UserDbStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserDbStorageTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindUserById() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUser);

        // вызываем тестируемый метод
        User savedUser = userStorage.getUser(1).get();

        // проверяем утверждения
        assertThat(savedUser)
                .isNotNull() // проверяем, что объект не равен null
                .usingRecursiveComparison() // проверяем, что значения полей нового
                .isEqualTo(newUser);        // и сохраненного пользователя - совпадают
    }

    @Test
    public void testFindUserFailById() {
        // Подготавливаем данные для теста
        User newUser = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUser);

        assertThrows(NoSuchElementException.class, () -> {
            User savedUser = userStorage.getUser(2).get();
        });   // и сохраненного пользователя - совпадают
    }

    @Test
    public void testDeleteUserByIdAndGetList() {
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov",
                LocalDate.of(1996, 3, 13));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUserOne);
        userStorage.addUser(newUserTwo);

        List<User> userList = userStorage.getUserList();

        assertEquals(2, userList.size());

        userStorage.deleteUser(2L);

        List<User> newUserList = userStorage.getUserList();

        assertEquals(1, newUserList.size());
    }

    @Test
    public void testDeleteUserByFailId() {
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        UserDbStorage userStorage = new UserDbStorage(jdbcTemplate);
        userStorage.addUser(newUserOne);
        assertThrows(NotExistUserException.class, () -> {
            userStorage.deleteUser(2L);
        });
    }

    @Test
    public void testUpdateUser() {
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(1L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov", LocalDate.of(1996, 3, 13));
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        userDbStorage.addUser(newUserOne);

        userDbStorage.updateUser(newUserTwo);
        User updateUser = userDbStorage.getUser(1l).get();

        assertThat(updateUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newUserTwo);
    }

    @Test
    public void testUpdateFailUser() {
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov", LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov", LocalDate.of(1996, 3, 13));
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        userDbStorage.addUser(newUserOne);

        assertThrows(NotExistUserException.class, () -> {
            userDbStorage.updateUser(newUserTwo);;
        });
    }
}
