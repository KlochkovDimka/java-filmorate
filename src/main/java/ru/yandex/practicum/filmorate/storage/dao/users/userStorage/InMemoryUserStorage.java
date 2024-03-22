package ru.yandex.practicum.filmorate.storage.dao.users.userStorage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistUserException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.dao.users.UserStorage;

import java.util.*;

@Component
@Qualifier("InMemoryUserStorage")
@Slf4j
public class InMemoryUserStorage implements UserStorage {

    private int generatedId = 1;

    private final Map<Long, User> users = new HashMap<>();

    //Добавление пользователя
    @Override
    public User addUser(User user) {
        user.setId(assignId());
        users.put(user.getId(), user);
        return user;
    }

    //Удаление пользователя
    @Override
    public void deleteUser(long id) {
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
    public Optional<User> getUser(long id) {
        isUser(id);
        return Optional.of(users.get(id));
    }

    //Получение списка пользователей
    @Override
    public List<User> getUserList() {
        return new ArrayList<>(users.values());
    }

    //Проверка на наличие пользователя
    public void isUser(long idUser) {
        if (!users.containsKey(idUser)) {
            throw new NotExistUserException(String.format("Пользователь c id: $s, не найден", idUser));
        }
    }

    private int assignId() {
        return generatedId++;
    }

}
