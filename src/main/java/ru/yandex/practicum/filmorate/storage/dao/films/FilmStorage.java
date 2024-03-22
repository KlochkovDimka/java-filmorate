package ru.yandex.practicum.filmorate.storage.dao.films;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film saveFilm(Film film);

    void deleteFilm(int id);

    void updateFilm(Film film);

    Optional<Film> getFilm(int id);

    List<Film> getListFilms();

    void isFilm(int filmId);

}
