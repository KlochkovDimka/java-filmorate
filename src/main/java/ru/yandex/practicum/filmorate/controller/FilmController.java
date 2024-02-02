package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private Map<Integer, Film> films = new HashMap();
    private int generatedId = 1;

    @PostMapping
    public Film postFilm(@Valid @RequestBody Film film) {
        if (validation(film)) {
            film.setId(assignId());
            films.put(film.getId(), film);
            log.debug("Успешно добавлен фильм: {}", film);
        }
        return film;
    }

    @PutMapping
    public Film putFilm(@RequestBody Film film) {
        if (!films.containsKey(film.getId())) throw new ValidationException();
        films.put(film.getId(), film);
        log.debug("Добавлен film: {}", film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    private int assignId() {
        return generatedId++;
    }

    private boolean validation(Film film) throws ValidationException {
        if (film.getDescription().length() > 201) {
            throw new ValidationException("Количество символов превышает 200");
        }
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new ValidationException("Неверная дата релиза фильма");
        }
        if (film.getDuration() <= 0) {
            throw new ValidationException("Отрицательная продолжительность фильма");
        }
        return true;
    }
}
