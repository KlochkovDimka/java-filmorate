package ru.yandex.practicum.filmorate.service.genre;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.genres.GenresStorageFilmsDao;

import java.util.List;

@Service
@Slf4j
public class GenreService {

    private final GenresStorageFilmsDao genresStorageFilmsDao;

    public GenreService(GenresStorageFilmsDao genresStorageFilmsDao) {
        this.genresStorageFilmsDao = genresStorageFilmsDao;
    }

    // Получение всех жанров
    public List<Genre> getAllGenres() {
        return genresStorageFilmsDao.findAllGenres();
    }

    // Получение жанра по id
    public Genre getGenreById(int genreId) {
        return genresStorageFilmsDao.getGenreById(genreId);
    }
}
