package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.exceptions.NotExistFilmException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.films.impl.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.dao.genres.impl.GenresFilmsStorageDaoImpl;
import ru.yandex.practicum.filmorate.storage.dao.mpa.impl.MpaStorageDaoImpl;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FilmDbStorageTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testGetFilmById() {
        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        filmDbStorage.saveFilm(newFilm);

        Film savedFilm = filmDbStorage.getFilm(1).get();

        assertThat(savedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilm);
    }

    @Test
    public void testGetFilmFailById() {
        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        filmDbStorage.saveFilm(newFilm);

        assertThrows(NotExistFilmException.class, () -> {
            filmDbStorage.getFilm(2);
        });
    }

    @Test
    public void testDeleteFilmByIdAndGetList() {
        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        Film newFilmTwo = new Film(2,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        filmDbStorage.saveFilm(newFilm);
        filmDbStorage.saveFilm(newFilmTwo);

        List<Film> filmList = filmDbStorage.getListFilms();

        assertEquals(2, filmList.size());

        filmDbStorage.deleteFilm(1);

        List<Film> updateFilmList = filmDbStorage.getListFilms();

        assertEquals(1, updateFilmList.size());
    }

    @Test
    public void testUpdateFilm() {
        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        Film newFilmTwo = new Film(1,
                "astral",
                "description",
                LocalDate.of(2004, 03, 25),
                120,
                new Mpa(1, "G"));

        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        filmDbStorage.saveFilm(newFilm);
        filmDbStorage.updateFilm(newFilmTwo);

        Film updateFilm = filmDbStorage.getFilm(1).get();

        assertThat(updateFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(newFilmTwo);
    }

    @Test
    public void testUpdateFailFilm() {

        Film newFilm = new Film(1,
                "nisi eiusmod",
                "adipisicing",
                LocalDate.of(1967, 03, 25),
                100,
                new Mpa(1, "G"));
        Film newFilmTwo = new Film(2,
                "astral",
                "description",
                LocalDate.of(2004, 03, 25),
                120,
                new Mpa(1, "G"));

        GenresFilmsStorageDaoImpl genresFilmsStorageDao = new GenresFilmsStorageDaoImpl(jdbcTemplate);
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);
        FilmDbStorage filmDbStorage = new FilmDbStorage(jdbcTemplate, genresFilmsStorageDao, mpaStorageDao);
        filmDbStorage.saveFilm(newFilm);

        assertThrows(NotExistFilmException.class, () -> {
            filmDbStorage.updateFilm(newFilmTwo);
        });
    }


}
