package ru.yandex.practicum.filmorate.storage.dao.mpa.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotExistMpaException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.mpa.MpaStorageDao;

import java.util.List;

@Component
@Slf4j
public class MpaStorageDaoImpl implements MpaStorageDao {

    private final JdbcTemplate jdbcTemplate;

    public MpaStorageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sql = "SELECT * FROM MPA m;";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            return new Mpa(rs.getInt("MPA_ID"),
                    rs.getString("NAME"));
        });
    }

    @Override
    public Mpa getMpaById(int mpaId) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet("SELECT * FROM MPA m WHERE m.MPA_ID=?;", mpaId);

        if (rowSet.next()) {
            Mpa mpa = new Mpa(
                    rowSet.getInt("MPA_ID"),
                    rowSet.getString("NAME"));
            log.info("Получи MPA {}", mpa);
            return mpa;
        } else {
            throw new NotExistMpaException(String.format("Рейтинга с id = %s не существует", mpaId));
        }
    }
}
