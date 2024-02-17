package ru.yandex.practicum.filmorate.storage.user.userStorage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;

public interface UserStorage {

    void addUser(User user);

    void deleteUser(int id);

    void updateUser(User user);

    User getUser(int id);

    ArrayList<User> getUserList();
}
