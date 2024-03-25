package ru.yandex.practicum.filmorate.storage.dao.genres.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistGenreException;
import ru.yandex.practicum.filmorate.storage.dao.genres.GenresStorageFilmsDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
@Slf4j
public class GenresFilmsStorageDaoImpl implements GenresStorageFilmsDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GenresFilmsStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> findGenresFilmById(Integer idFilm) {
        String sql = "SELECT g.* " +
                "FROM GENRES_FILMS gf " +
                "INNER JOIN FILMS f ON f.ID = gf.FILM_ID " +
                "INNER JOIN GENRES g ON gf.GENRES_ID = g.ID " +
                "WHERE  gf.FILM_ID = ? " +
                "ORDER BY  gf.FILM_ID ";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Genre(rs.getInt("ID"), rs.getString("NAME"));
        }, idFilm);
    }

    @Override
    public void addNewGenre(Genre genre) {
        String sql = "INSERT INTO PUBLIC.GENRES" +
                "VALUES(?,?)";
        jdbcTemplate.update(sql, genre.getId(), genre.getName());
    }

    @Override
    public List<Genre> findAllGenres() {
        String sql = "SELECT* FROM GENRES;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Genre(rs.getInt("id"),
                    rs.getString("name"));
        });
    }

    @Override
    public Genre getGenreById(int genreId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM GENRES g WHERE g.ID = ?;", genreId);

        if (rowSet.next()) {
            Genre genre = new Genre(rowSet.getInt("id"),
                    rowSet.getString("name"));

            log.info("Получен жанр {}", genre);
            return genre;
        }
        throw new NotExistGenreException(String.format("Жанра с id = %s не существует", genreId));
    }
}
