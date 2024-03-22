package ru.yandex.practicum.filmorate.storage.dao.likes;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.List;

public interface LikesFilmsStorageDao {
    void addFilmLike(int filmId, int userId);

    void deleteFilmLike(int filmId, int userId);

    List<Film> getListPopularFilm(int count);
}
