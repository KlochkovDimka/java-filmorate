package ru.yandex.practicum.filmorate.storage.dao.films.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistMpaBadRequestException;
import ru.yandex.practicum.filmorate.storage.dao.films.FilmStorage;
import ru.yandex.practicum.filmorate.storage.dao.genres.impl.GenresFilmsStorageDaoImpl;
import ru.yandex.practicum.filmorate.exceptions.NotExistFilmException;
import ru.yandex.practicum.filmorate.exceptions.NotExistMpaException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.mpa.impl.MpaStorageDaoImpl;

import java.util.List;

@Primary
@Component
@Qualifier("filmDbStorage")
@Slf4j
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final GenresFilmsStorageDaoImpl genresFilmsStorageDao;
    private final MpaStorageDaoImpl mpaStorageDao;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenresFilmsStorageDaoImpl genresFilmsStorageDao, MpaStorageDaoImpl mpaStorageDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genresFilmsStorageDao = genresFilmsStorageDao;
        this.mpaStorageDao = mpaStorageDao;
    }

    // Сохранение фильма в базу данных
    @Override
    public Film saveFilm(Film film) {
        isMpaById(film.getMpa().getId());
        String sql =
                "INSERT INTO PUBLIC.FILMS (NAME,DESCRIPTION,RELEASEDATE,DURATION, MPA_ID) VALUES (?,?,?,?,?)";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId());

        film.setId(getNowFilmId());  // Присваиваем фильму Id
        addGenresFilm(film.getId(), film.getGenres());  //Добавляем в таблицу genres_films значения
        film.getMpa().setName(getNameMpa(film.getMpa().getId()));    // Присваиваем имя рейтингу
        log.info("Добавлен фильм {}", film.toString());
        return film;
    }

    @Override
    public void deleteFilm(int id) {
        isFilm(id);
        String sql = "delete from films where id=?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateFilm(Film film) {
        isMpaById(film.getMpa().getId());
        isFilm(film.getId());
        String sql = "UPDATE FILMS f\n" +
                "SET f.NAME = ?,\n" +
                "f.DESCRIPTION = ?,\n" +
                "f.RELEASEDATE = ?,\n" +
                "f.DURATION = ?\n" +
                "WHERE f.ID = ?;";

        jdbcTemplate.update(sql,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId());

        log.info("Обновлен фильм: {}", film);
    }

    @Override
    public Film getFilm(int id) {
        isFilm(id);
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet("SELECT* FROM FILMS WHERE ID=?", id);

        if (filmRow.next()) {
            Film film = new Film(
                    filmRow.getInt("id"),
                    filmRow.getString("name"),
                    filmRow.getString("description"),
                    filmRow.getDate("releaseDate").toLocalDate(),
                    filmRow.getLong("duration"),
                    mpaStorageDao.getMpaById(filmRow.getInt("mpa_id")));
            return film;
        }
        throw new NotExistFilmException("Not found film");
    }

    @Override
    public List<Film> getListFilms() {
        String sql = "SELECT* FROM FILMS;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
                    return new Film(rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getDate("releaseDate").toLocalDate(),
                            rs.getLong("duration"),
                            mpaStorageDao.getMpaById(rs.getInt("mpa_id")));
                }
        );
    }

    @Override
    public void isFilm(int filmId) {
        String sql = "SELECT f.ID FROM FILMS f WHERE f.ID=?";

        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, filmId);
        if (!rowSet.next()) {
            throw new NotExistFilmException(String.format("Фильм с id = %s не найден", filmId));
        }
    }

    public String getNameMpa(int mpaId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT  m.NAME  FROM MPA m  where m.MPA_ID = ?;", mpaId);
        if (rowSet.next()) {
            return rowSet.getString("name");
        }
        throw new NotExistMpaException("Данного рейтинга не существует");
    }

    private void isMpaById(int mpaId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT* FROM MPA m  where m.MPA_ID = ?;", mpaId);
        if (!rowSet.next()) {
            throw new NotExistMpaBadRequestException("Данного рейтинга не существует");
        }
    }

    private void addGenresFilm(int filmId, List<Genre> genres) {
        String sql = "INSERT INTO PUBLIC.GENRES_FILMS(FILM_ID, GENRES_ID) " +
                "VALUES(?,?)";
        genres.forEach(genre -> jdbcTemplate.update(sql, filmId, genre.getId()));
    }

    private int getNowFilmId() {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT f.ID  FROM public.FILMS f " +
                "ORDER BY f.ID DESC LIMIT 1;");
        if (sqlRowSet.next()) {
            return sqlRowSet.getInt("id");
        }
        throw new NotExistFilmException("Ошибка добавления фильма");
    }
}