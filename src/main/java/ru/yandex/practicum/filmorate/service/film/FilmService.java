package ru.yandex.practicum.filmorate.service.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    private final InMemoryFilmStorage inMemoryFilmStorage;
    private final InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
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
                .sorted((f1, f2) -> {
                    if (listSortFilm.size() == 1 && f1.getLikesFromUsers().size() == 0) return 1;
                    int temp = f1.getLikesFromUsers().size() - f2.getLikesFromUsers().size();
                    return temp * -1;
                })
                .limit(count)
                .collect(Collectors.toList());
    }

}
