package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.filmStorage.FilmStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private int generatedId = 1;

    private final Map<Integer, Film> films = new HashMap<>();

    //Добавление нового фильма
    @Override
    public void addFilm(Film film) {
        film.setId(assignID());
        films.put(film.getId(), film);
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
    public Film getFilm(int id) {
        isFilm(id);
        return films.get(id);
    }

    //Получение списка фильмов
    @Override
    public ArrayList<Film> getListFilms() {
        return new ArrayList<>(films.values());
    }

    //Проверка на наличие фильма по идентификационному номеру
    public void isFilm(int filmId) {
        if (!films.containsKey(filmId)) throw new NotExistFilmException("Фильма с данным id не существует");
    }

    private int assignID() {
        return generatedId++;
    }

}
