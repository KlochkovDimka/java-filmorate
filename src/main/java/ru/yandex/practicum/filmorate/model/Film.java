package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Film.
 */
@Data
public class Film {

    private Set<Integer> likesFromUsers = new HashSet<>();

    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Size(min = 1, max = 201)
    private String description;

    private LocalDate releaseDate;

    @Positive
    private long duration;

    //Добавление лайка
    public void addLike(int userId) {
        likesFromUsers.add(userId);
    }

    //Удаление лайка
    public void deleteLike(int userId) {
        likesFromUsers.remove(userId);
    }
}
