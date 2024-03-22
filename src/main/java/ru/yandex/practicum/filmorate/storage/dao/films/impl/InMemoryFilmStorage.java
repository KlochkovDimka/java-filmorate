package ru.yandex.practicum.filmorate.storage.dao.films.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.films.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Qualifier("InMemoryFilmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private int generatedId = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    //Добавление нового фильма
    @Override
    public Film saveFilm(Film film) {
        film.setId(assignID());
        films.put(film.getId(), film);
        return film;
    }

    //Удаление фильма по идентификационному номеру
    @Override
    public void deleteFilm(int id) {
        isFilm(id);
        films.remove(id);
    }

    //Обновление фильма
    @Override
    public void updateFilm(Film film) {
        isFilm(film.getId());
        films.put(film.getId(), film);
    }

    //Получение фильма по идентификационному номеру
    @Override
    public Optional<Film> getFilm(int id) {
        isFilm(id);
        return Optional.of(films.get(id));
    }

    //Получение списка фильмов
    @Override
    public ArrayList<Film> getListFilms() {
        return new ArrayList<>(films.values());
    }

    //Проверка на наличие фильма по идентификационному номеру
    public void isFilm(int filmId) {
        if (!films.containsKey(filmId)) {
            throw new NotExistFilmException("Фильма с данным id не существует");
        }
    }

    private int assignID() {
        return generatedId++;
    }

}
