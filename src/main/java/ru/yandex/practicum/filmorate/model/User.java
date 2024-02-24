package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * User.
 */
@Data
public class User {

    private int id;
    private Set<Integer> friends = new HashSet<>();

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @NotBlank
    private String login;

    private String name;

    @Past
    private LocalDate birthday;

    public Set<Integer> getFriends() {
        return new HashSet<>(friends);
    }

    //Добавление в друзья
    public void addFriends(int idFriend) {
        friends.add(idFriend);
    }

    //Удаление из друзей
    public void removeFriend(int idFriend) {
        friends.remove(idFriend);
    }


}
