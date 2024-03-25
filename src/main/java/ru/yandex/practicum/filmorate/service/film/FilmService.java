package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.storage.dao.likes.LikesFilmsStorageDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.films.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.users.UserStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {

    @Qualifier("filmDbStorage")
    private final FilmStorage filmStorage;
    @Qualifier("UserDbStorage")
    private final UserStorage userStorage;
    private final LikesFilmsStorageDao likesFilmsStorageDao;

    @Autowired
    public FilmService(FilmStorage inMemoryFilmStorage, UserStorage inMemoryUserStorage, LikesFilmsStorageDao likesFilmsStorageDao) {
        this.filmStorage = inMemoryFilmStorage;
        this.userStorage = inMemoryUserStorage;
        this.likesFilmsStorageDao = likesFilmsStorageDao;
    }

    // Сохранение фильма
    public void addFilm(Film film) {
        filmStorage.saveFilm(film);
    }

    // Обновление фильма по id
    public void updateFilm(Film film) {
        filmStorage.updateFilm(film);
    }

    // Получение всех фильмов
    public List<Film> getListFilm() {
        return filmStorage.getListFilms();
    }

    // Получение фильма по id
    public Film getFilmById(int filmId) {
        return filmStorage.getFilm(filmId);
    }

    //Добавление лайка фильму по идентификационным номерам фильма и пользователя
    public void addFilmLike(int filmId, int userId) {
        likesFilmsStorageDao.addFilmLike(filmId, userId);
    }

    //Удаление лайка у фильма по идентификационным номерам фильма и пользователя
    public void deleteFilmLike(int filmId, int userId) {
        likesFilmsStorageDao.deleteFilmLike(filmId, userId);
    }

    //Получение популярных фильмов
    public List<Film> getListPopularFilm(int count) {
        return likesFilmsStorageDao.getListPopularFilm(count);
    }
}
