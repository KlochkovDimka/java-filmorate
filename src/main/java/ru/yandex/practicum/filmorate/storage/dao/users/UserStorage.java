package ru.yandex.practicum.filmorate.storage.dao.users;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {

    User addUser(User user);

    void deleteUser(long id);

    void updateUser(User user);

    User getUser(long id);

    List<User> getUserList();

    void isUser(long userId);
}
