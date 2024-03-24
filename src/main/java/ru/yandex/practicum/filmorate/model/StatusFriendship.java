package ru.yandex.practicum.filmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class StatusFriendship {
    @Positive
    private int id;
    @NotNull
    private String name;
}
