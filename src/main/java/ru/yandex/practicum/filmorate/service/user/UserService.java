package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.dao.friendship.FriendshipDbStorageDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.users.UserStorage;

import java.util.List;

@Service
@Slf4j
public class UserService {

    @Qualifier("UserDbStorage")
    private final UserStorage userStorage;
    private final FriendshipDbStorageDao friendshipStorageDao;

    @Autowired
    public UserService(UserStorage userStorage, FriendshipDbStorageDao friendshipStorageDao) {
        this.userStorage = userStorage;
        this.friendshipStorageDao = friendshipStorageDao;
    }

    // Добавить пользователя
    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    //Обновить пользователя
    public void updateUser(User user) {
        userStorage.updateUser(user);
    }

    // Получить список пользователей
    public List<User> getUserList() {
        return userStorage.getUserList();
    }

    // Получить пользователя по id
    public User getUserById(int userId) {
        return userStorage.getUser(userId).get();
    }

    //Удаление пользователя по идентификатору
    public void deleteUserById(int userID) {
        userStorage.deleteUser(userID);
    }

    //Добавление в друзья
    public void addInFriends(Long userId, Long friendId, Integer statusId) {
        friendshipStorageDao.addInListFriendsById(userId, friendId, statusId);
    }

    //Удаление из друзей
    public void deleteFromFriends(Long idUser, Long idFriend) {
        friendshipStorageDao.deleteOfListFriendsById(idUser, idFriend);
    }

    //Получение списка общих друзей двух пользователей
    public List<User> getListCommonFriends(Long idUser, Long idFriend) {
        return friendshipStorageDao.findListCommonFriendsById(idUser, idFriend);
    }

    // Получение списка друзей конкретного пользователя
    public List<User> getFriendsList(Long idUser) {
        return friendshipStorageDao.findFriendsUserById(idUser);
    }
}
