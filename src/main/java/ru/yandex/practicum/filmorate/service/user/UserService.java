package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.userStorage.UserStorage;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final UserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage memoryUserStorage) {
        this.inMemoryUserStorage = memoryUserStorage;
    }

    public void addUser(User user) {
        inMemoryUserStorage.addUser(user);
    }

    public void updateUser(User user) {
        inMemoryUserStorage.updateUser(user);
    }

    public ArrayList<User> getUserList() {
        return inMemoryUserStorage.getUserList();
    }

    public User getUserById(int userId) {
        return inMemoryUserStorage.getUser(userId);
    }

    //Добавление в друзья
    public void addInFriends(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.isUser(idUser);
        inMemoryUserStorage.isUser(idFriend);

        inMemoryUserStorage.getUser(idUser).addFriends(idFriend);
        inMemoryUserStorage.getUser(idFriend).addFriends(idUser);
    }

    //Удаление из друзей
    public void deleteFromFriends(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.isUser(idUser);
        inMemoryUserStorage.isUser(idFriend);
        inMemoryUserStorage.getUser(idUser).removeFriend(idFriend);
    }

    //Получение списка общих друзей двух пользователей
    public ArrayList<User> getListCommonFriends(Integer idUser, Integer idFriend) {
        inMemoryUserStorage.isUser(idUser);
        inMemoryUserStorage.isUser(idFriend);

        Set<Integer> user = inMemoryUserStorage.getUser(idUser).getFriends();
        Set<Integer> friend = inMemoryUserStorage.getUser(idFriend).getFriends();

        ArrayList<Integer> mutualFriends = new ArrayList<>(user);
        mutualFriends.retainAll(friend);

        ArrayList<User> users = new ArrayList<>();
        mutualFriends.stream()
                .map(i -> users.add(inMemoryUserStorage.getUser(i)))
                .collect(Collectors.toList());
        return users;
    }

    // Получение списка друзей конкретного пользователя
    public ArrayList<User> getFriendsList(int idUser) {
        inMemoryUserStorage.isUser(idUser);

        ArrayList<User> userFriend = new ArrayList<>();
        inMemoryUserStorage.getUser(idUser).getFriends().stream()
                .map(integer -> userFriend.add(inMemoryUserStorage.getUser(integer)))
                .collect(Collectors.toList());
        return userFriend;
    }
}
