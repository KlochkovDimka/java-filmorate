package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotParamFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.film.FilmService;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @PostMapping()
    public Film postFilm(@Valid @RequestBody Film film) {
        validation(film);
        inMemoryFilmStorage.addFilm(film);
        return film;
    }

    @PutMapping
    public Film putFilm(@Valid @RequestBody Film film) {
        validation(film);
        inMemoryFilmStorage.updateFilm(film);
        return film;
    }

    @GetMapping
    public ArrayList<Film> getFilms() {
        return inMemoryFilmStorage.getListFilms();
    }

    @GetMapping("{id}")
    public Film getFilmById(@PathVariable("id") int filmId) {
        return inMemoryFilmStorage.getFilm(filmId);
    }

    @PutMapping("{id}/like/{userId}")
    public void putLikeByFilm(@PathVariable("id") int id,
                              @PathVariable("userId") int userId) {
        filmService.addFilmLike(id, userId);
    }

    @DeleteMapping("{id}/like/{userId}")
    public void deleteLikeByFilm(@PathVariable("id") int id,
                                 @PathVariable("userId") int userId) {
        filmService.deleteFilmLike(id, userId);
    }

    @GetMapping("/popular")
    public ArrayList<Film> getPopularFilm(
            @RequestParam(defaultValue = "10", required = false) Integer count) {
        return (ArrayList<Film>) filmService.getListPopularFilm(count);
    }

    private void validation(Film film) throws NotParamFilmException {
        if (film.getReleaseDate().isBefore(LocalDate.parse("1895-12-28"))) {
            throw new NotParamFilmException("Неверная дата релиза фильма");
        }
    }
}
