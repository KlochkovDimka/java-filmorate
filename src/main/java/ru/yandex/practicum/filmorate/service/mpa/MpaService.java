package ru.yandex.practicum.filmorate.service.mpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.mpa.MpaStorageDao;

import javax.validation.constraints.AssertFalse;
import java.util.List;

@Service
@Slf4j
public class MpaService {
    private final MpaStorageDao mpaStorageDao;

    @Autowired
    public MpaService(MpaStorageDao mpaStorageDao) {
        this.mpaStorageDao = mpaStorageDao;
    }

    // Получение списка рейтингов
    public List<Mpa> getAllMpa() {
        return mpaStorageDao.getAllMpa();
    }

    // Получение рейтинга по id
    public Mpa getMpaById(int id) {
        return mpaStorageDao.getMpaById(id);
    }
}
