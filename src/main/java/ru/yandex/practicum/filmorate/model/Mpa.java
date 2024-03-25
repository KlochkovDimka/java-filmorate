package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class Mpa {
    @Positive
    private int id;
    @NotNull
    private String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
