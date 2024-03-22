package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Film.
 */
@Setter
@Getter
@ToString
public class Film {
    private Set<Integer> likesFromUsers = new HashSet<>();

    private Integer id;

    @NotNull
    @NotBlank
    private String name;

    @Size(min = 1, max = 201)
    private String description;
    private LocalDate releaseDate;
    @Positive
    private long duration;

    private Mpa mpa;

    private List<Genre> genres = new ArrayList<>();

    public Set<Integer> getLikesFromUsers() {
        return new HashSet<>(likesFromUsers);
    }

    //Добавление лайка
    public void addLike(int userId) {
        likesFromUsers.add(userId);
    }

    //Удаление лайка
    public void deleteLike(int userId) {
        likesFromUsers.remove(userId);
    }

    public void addGenreToFilm(Genre genre) {
        genres.add(genre);
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public Film(int id, String name, String description, LocalDate releaseDate, long duration, Mpa mpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
    }
}