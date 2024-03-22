package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.mpa.impl.MpaStorageDaoImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MpaStorageDaoImplTest {

    private final JdbcTemplate jdbcTemplate;

    @Test
    public void getAllMpaTest() {
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);

        List<Mpa> list = mpaStorageDao.getAllMpa();

        assertNotNull(list);
    }

    @Test
    public void getMpaByIdTest() {
        MpaStorageDaoImpl mpaStorageDao = new MpaStorageDaoImpl(jdbcTemplate);

        Mpa mpa = mpaStorageDao.getMpaById(2);

        assertNotNull(mpa.getId());
        assertNotNull(mpa.getName());
    }
}
