package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotExistGenreException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.films.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.genres.impl.GenresFilmsStorageDaoImpl;
import ru.yandex.practicum.filmorate.storage.dao.mpa.impl.MpaStorageDaoImpl;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class GenresFilmsStorageDaoImplTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void findGenresFilmById(){
        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        newFilm.setGenres(List.of(new Genre(1,"Комедия"), new Genre(2,"Драма")));
        filmDbStorage.saveFilm(newFilm);

       List<Genre> list = genresFilmsStorageDao.findGenresFilmById(newFilm.getId());

        assertEquals(2,list.size());
    }

    @Test
    public void findAllGenresTest(){
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        List<Genre> genreList = genresFilmsStorageDao.findAllGenres();

        assertNotNull(genreList.size());
    }

    @Test
    public void getGenreByIdTest(){
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);

        Genre genre = genresFilmsStorageDao.getGenreById(1);

        assertNotNull(genre);
    }

    @Test
    public void getGenreByFailIdTest(){
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);

        assertThrows(NotExistGenreException.class, ()->{
            genresFilmsStorageDao.getGenreById(12);
        });
    }
}
