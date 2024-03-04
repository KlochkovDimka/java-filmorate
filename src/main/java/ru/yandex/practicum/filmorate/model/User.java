package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * User.
 */
@Data
public class User {

    private int id;
    private Map<Integer, StatusFriendship> friends = new HashMap<>();

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String login;

    private String name;

    @Past
    private LocalDate birthday;

    public Set<Integer> getFriends() {
        return new HashSet<>(friends.keySet());
    }

    //Добавление в друзья
    public void addFriends(int idFriend, StatusFriendship status) {
        friends.put(idFriend, status);
    }

    //Удаление из друзей
    public void removeFriend(int idFriend) {
        friends.remove(idFriend);
    }


}
