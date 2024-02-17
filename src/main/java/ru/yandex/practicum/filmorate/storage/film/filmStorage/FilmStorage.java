package ru.yandex.practicum.filmorate.storage.film.filmStorage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    void addFilm(Film film);

    void deleteFilm(int id);

    void updateFilm(Film film);

    Film getFilm(int id);

    ArrayList<Film> getListFilms();
}
