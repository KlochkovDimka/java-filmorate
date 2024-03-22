package ru.yandex.practicum.filmorate.model;

import lombok.*;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Service
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StatusFriendship {
    @Positive
    private int id;
    @NotNull
    private String name;
}
