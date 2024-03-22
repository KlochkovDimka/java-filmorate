package ru.yandex.practicum.filmorate.storage.dao.mpa;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaStorageDao {

    List<Mpa> getAllMpa();

    Mpa getMpaById(int mpaId);
}
