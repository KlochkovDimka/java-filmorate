package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private Map<Integer, User> users = new HashMap<>();
    private int generatedId = 1;

    @GetMapping
    public ArrayList<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @PostMapping
    public User postUser(@Valid @RequestBody User user) {
        isValidation(user);

        user.setId(assignId());
        users.put(user.getId(), user);
        log.debug("Добавлен пользователь {}", user);

        return user;
    }

    @PutMapping
    public User putUser(@RequestBody User user) {
        if (!users.containsKey(user.getId())) throw new ValidationException();
        users.put(user.getId(), user);
        log.debug("Обновлен user {}", user);
        return user;
    }

    private int assignId() {
        return generatedId++;
    }

    private void isValidation(User user) throws ValidationException {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
