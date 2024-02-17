package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final InMemoryUserStorage inMemoryUserStorage;
    private final UserService userService;

    @Autowired
    public UserController(InMemoryUserStorage inMemoryUserStorage, UserService userService) {
        this.inMemoryUserStorage = inMemoryUserStorage;
        this.userService = userService;
    }

    @GetMapping
    public ArrayList<User> getUsers() {
        return inMemoryUserStorage.getUserList();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        isValidation(user);
        inMemoryUserStorage.addUser(user);
        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        isValidation(user);
        inMemoryUserStorage.updateUser(user);
        return user;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") int id) {
        return inMemoryUserStorage.getUser(id);
    }

    @PutMapping("{id}/friends/{friendId}")
    public void putFriend(
            @PathVariable("id") Integer idUser,
            @PathVariable("friendId") Integer idFriend) {

        userService.addInFriends(idUser, idFriend);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") Integer idUser,
            @PathVariable("friendId") Integer idFriend) {

        userService.deleteFromFriends(idUser, idFriend);
    }

    @GetMapping("{id}/friends")
    public ArrayList<User> getUserFriends(@PathVariable("id") int userId) {
        return userService.getFriendsList(userId);
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public ArrayList<User> getCommonFriends(
            @PathVariable int id,
            @PathVariable int otherId) {

        return userService.getListCommonFriends(id, otherId);
    }

    private void isValidation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
