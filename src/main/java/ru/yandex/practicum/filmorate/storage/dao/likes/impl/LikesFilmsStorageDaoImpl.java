package ru.yandex.practicum.filmorate.storage.dao.likes.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.dao.likes.LikesFilmsStorageDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.films.impl.FilmDbStorage;

import java.util.List;

@Component
public class LikesFilmsStorageDaoImpl implements LikesFilmsStorageDao {

    private final JdbcTemplate jdbcTemplate;
    private final FilmDbStorage filmStorage;

    @Autowired
    public LikesFilmsStorageDaoImpl(JdbcTemplate jdbcTemplate, FilmDbStorage filmStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmStorage = filmStorage;
    }


    @Override
    public void addFilmLike(int filmId, int userId) {
        String sql = "INSERT INTO LIKES_FILMS(FILM_ID, USER_ID) VALUES(?,?);";
        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public void deleteFilmLike(int filmId, int userId) {
        String sql = "DELETE FROM LIKES_FILMS " +
                "WHERE FILM_ID = ? AND USER_ID = ?;";

        jdbcTemplate.update(sql, filmId, userId);
    }

    @Override
    public List<Film> getListPopularFilm(int count) {
        String sql = "SELECT f.* " +
                "FROM LIKES_FILMS lf " +
                "INNER JOIN FILMS f ON f.ID = lf.FILM_ID " +
                "GROUP BY f.ID " +
                "ORDER BY COUNT(lf.USER_ID) DESC " +
                "LIMIT ?;";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Film(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("releaseDate").toLocalDate(),
                    rs.getLong("duration"),
                    new Mpa(rs.getInt("mpa_id"), filmStorage.getNameMpa(rs.getInt("mpa_id"))));
        }, count);
    }
}
