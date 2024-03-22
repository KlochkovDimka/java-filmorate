package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.friendship.impl.FriendshipDbStorageDaoImpl;
import ru.yandex.practicum.filmorate.storage.dao.users.userStorage.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest // указываем, о необходимости подготовить бины для работы с БД
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FriendshipDbStorageDaoTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testFindFriendsUserById(){
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        FriendshipDbStorageDaoImpl friendshipStorageDao = new FriendshipDbStorageDaoImpl(jdbcTemplate);

        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov",
                LocalDate.of(1996, 3, 13));

        userDbStorage.addUser(newUserOne);
        userDbStorage.addUser(newUserTwo);

        friendshipStorageDao.addInListFriendsById(newUserOne.getId(), newUserTwo.getId(), 1);
        List<User> friendsList = friendshipStorageDao.findFriendsUserById(newUserOne.getId());

        assertEquals(1, friendsList.size());
    }
    @Test
    public void testFindFriendsUserFailById(){
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        FriendshipDbStorageDaoImpl friendshipStorageDao = new FriendshipDbStorageDaoImpl(jdbcTemplate);

        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov",
                LocalDate.of(1996, 3, 13));

        userDbStorage.addUser(newUserOne);
        userDbStorage.addUser(newUserTwo);

        friendshipStorageDao.addInListFriendsById(newUserOne.getId(), newUserTwo.getId(), 1);

        assertThrows(NotExistUserException.class, ()->{
            friendshipStorageDao.findFriendsUserById(3L);
        });
    }
    @Test
    public void testDeleteFriendById(){
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        FriendshipDbStorageDaoImpl friendshipStorageDao = new FriendshipDbStorageDaoImpl(jdbcTemplate);
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov",
                LocalDate.of(1996, 3, 13));

        userDbStorage.addUser(newUserOne);
        userDbStorage.addUser(newUserTwo);

        friendshipStorageDao.addInListFriendsById(newUserOne.getId(), newUserTwo.getId(), 1);

        friendshipStorageDao.deleteOfListFriendsById(newUserOne.getId(), newUserTwo.getId());
        List<User> userList = friendshipStorageDao.findFriendsUserById(newUserOne.getId());
        assertEquals(0, userList.size());
    }

    @Test
    public void testFindListCommonFriendsById(){
        UserDbStorage userDbStorage = new UserDbStorage(jdbcTemplate);
        FriendshipDbStorageDaoImpl friendshipStorageDao = new FriendshipDbStorageDaoImpl(jdbcTemplate);
        User newUserOne = new User(1L, "user@email.ru", "vanya123", "Ivan Petrov",
                LocalDate.of(1990, 1, 1));
        User newUserTwo = new User(2L, "userTwo@email.ru", "Ivan123", "Ivan Ivanov",
                LocalDate.of(1996, 3, 13));
        User newUserCommon = new User(3L, "userCommon@email.ru", "Artem123", "Artem Ivanov",
                LocalDate.of(1996, 3, 13));

        userDbStorage.addUser(newUserOne);
        userDbStorage.addUser(newUserTwo);
        userDbStorage.addUser(newUserCommon);

        friendshipStorageDao.addInListFriendsById(newUserOne.getId(), newUserCommon.getId(), 1);
        friendshipStorageDao.addInListFriendsById(newUserTwo.getId(), newUserCommon.getId(), 1);

        List<User> userList = friendshipStorageDao.findListCommonFriendsById(newUserOne.getId(), newUserTwo.getId());

        User getFriendCommon = userList.get(0);
        assertEquals(newUserCommon.getId(), getFriendCommon.getId());
        assertEquals(newUserCommon.getName(), getFriendCommon.getName());
    }
}
