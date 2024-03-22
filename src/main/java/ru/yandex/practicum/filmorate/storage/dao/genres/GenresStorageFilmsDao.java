package ru.yandex.practicum.filmorate.storage.dao.genres;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenresStorageFilmsDao {

    List<Genre> findGenresFilmById(Integer idFilm);

    List<Genre> findAllGenres();

    Genre getGenreById(int genreId);

    void addNewGenre(Genre genre);
}
