package ru.yandex.practicum.filmorate.service.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    private final InMemoryUserStorage memoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage memoryUserStorage) {
        this.memoryUserStorage = memoryUserStorage;
    }

    //Добавление в друзья
    public void addInFriends(Integer idUser, Integer idFriend) {
        memoryUserStorage.isUser(idUser);
        memoryUserStorage.isUser(idFriend);

        memoryUserStorage.getUser(idUser).addFriends(idFriend);
        memoryUserStorage.getUser(idFriend).addFriends(idUser);
    }

    //Удаление из друзей
    public void deleteFromFriends(Integer idUser, Integer idFriend) {
        memoryUserStorage.isUser(idUser);
        memoryUserStorage.isUser(idFriend);
        memoryUserStorage.getUser(idUser).removeFriend(idFriend);
    }

    //Получение списка общих друзей двух пользователей
    public ArrayList<User> getListCommonFriends(Integer idUser, Integer idFriend) {
        memoryUserStorage.isUser(idUser);
        memoryUserStorage.isUser(idFriend);

        Set<Integer> user = memoryUserStorage.getUser(idUser).getFriends();
        Set<Integer> friend = memoryUserStorage.getUser(idFriend).getFriends();

        ArrayList<Integer> mutualFriends = new ArrayList<>(user);
        mutualFriends.retainAll(friend);

        ArrayList<User> users = new ArrayList<>();
        mutualFriends.stream()
                .map(i -> users.add(memoryUserStorage.getUser(i)))
                .collect(Collectors.toList());
        return users;
    }

    // Получение списка друзей конкретного пользователя
    public ArrayList<User> getFriendsList(int idUser) {
        memoryUserStorage.isUser(idUser);

        ArrayList<User> userFriend = new ArrayList<>();
        memoryUserStorage.getUser(idUser).getFriends().stream()
                .map(integer -> userFriend.add(memoryUserStorage.getUser(integer)))
                .collect(Collectors.toList());
        return userFriend;
    }

}
