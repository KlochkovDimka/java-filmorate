package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.userStorage.UserStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private int generatedId = 1;

    private final Map<Integer, User> users = new HashMap<>();

    //Добавление пользователя
    @Override
    public void addUser(User user) {
        user.setId(assignId());
        users.put(user.getId(), user);
    }

    //Удаление пользователя
    @Override
    public void deleteUser(int id) {
        isUser(id);
        users.remove(id);
    }

    //Обновление данных пользователя
    @Override
    public void updateUser(User user) {
        isUser(user.getId());
        users.put(user.getId(), user);
    }

    //Получение пользователя по идентификационному номеру
    @Override
    public User getUser(int id) {
        isUser(id);
        return users.get(id);
    }

    //Получение списка пользователей
    @Override
    public ArrayList<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    //Проверка на наличие пользователя
    public void isUser(int idUser) {
        if (!users.containsKey(idUser)) throw new NotExistUserException("Пользователь не найден" + idUser);
    }

    private int assignId() {
        return generatedId++;
    }

}
