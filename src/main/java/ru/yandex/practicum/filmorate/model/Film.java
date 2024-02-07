package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {

    private int id;

    @NotNull
    @NotBlank
    private String name;

    @Size(min = 1, max = 201)
    private String description;

    private LocalDate releaseDate;

    @Positive
    private long duration;

}
