package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.film.filmStorage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.user.userStorage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage inMemoryFilmStorage;
    private final UserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public void addFilm(Film film) {
        inMemoryFilmStorage.addFilm(film);
    }

    public void updateFilm(Film film) {
        inMemoryFilmStorage.updateFilm(film);
    }

    public ArrayList<Film> getListFilm() {
        return inMemoryFilmStorage.getListFilms();
    }

    public Film getFilmById(int filmId) {
        return inMemoryFilmStorage.getFilm(filmId);
    }

    //Добавление лайка фильму по идентификационным номерам фильма и пользователя
    public void addFilmLike(int filmId, int userId) {
        inMemoryFilmStorage.isFilm(filmId);
        inMemoryUserStorage.isUser(userId);

        inMemoryFilmStorage.getFilm(filmId).addLike(userId);

    }

    //Удаление лайка у фильма по идентификационным номерам фильма и пользователя
    public void deleteFilmLike(int filmId, int userId) {
        inMemoryFilmStorage.isFilm(filmId);
        inMemoryUserStorage.isUser(userId);

        inMemoryFilmStorage.getFilm(filmId).deleteLike(userId);
    }

    //Получение популярных фильмов
    public List<Film> getListPopularFilm(int count) {
        ArrayList<Film> listSortFilm = new ArrayList<>(inMemoryFilmStorage.getListFilms());

        return listSortFilm.stream()
                .sorted((f1, f2) -> compare(f1, f2))
                .limit(count)
                .collect(Collectors.toList());
    }

    private int compare(Film filmOne, Film filmTwo) {
        int temp = filmOne.getLikesFromUsers().size() - filmTwo.getLikesFromUsers().size();
        return temp * -1;
    }
}
