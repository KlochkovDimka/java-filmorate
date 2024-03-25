package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.storage.dao.friendship.FriendshipDbStorageDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final FriendshipDbStorageDao friendshipStorageDao;

    @Autowired
    public UserController(UserService userService, FriendshipDbStorageDao friendshipStorageDao) {
        this.userService = userService;
        this.friendshipStorageDao = friendshipStorageDao;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUserList();
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        isValidation(user);
        return userService.addUser(user);
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        isValidation(user);
        userService.updateUser(user);
        return user;
    }

    @GetMapping("{id}")
    public User getUserById(@PathVariable("id") int id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("{id}")
    public void deleteUserByID(@PathVariable("id") int id) {
        userService.deleteUserById(id);
    }


    @PutMapping("{id}/friends/{friendId}")
    public void putFriend(
            @PathVariable("id") Long idUser,
            @PathVariable("friendId") Long idFriend) {
        log.debug("userId = {}, friendId = {}", idUser, idFriend);
        friendshipStorageDao.addInListFriendsById(idUser, idFriend, 1);
    }

    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") Long idUser,
            @PathVariable("friendId") Long idFriend) {

        friendshipStorageDao.deleteOfListFriendsById(idUser, idFriend);
    }

    @GetMapping("{id}/friends")
    public List<User> getUserFriends(@PathVariable("id") Long userId) {
        List<User> userList = friendshipStorageDao.findFriendsUserById(userId);
        log.info("выполнен запрос на получение списка друзей");
        return userList;
    }

    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(
            @PathVariable Long id,
            @PathVariable Long otherId) {
        return friendshipStorageDao.findListCommonFriendsById(id, otherId);
    }


    private void isValidation(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
