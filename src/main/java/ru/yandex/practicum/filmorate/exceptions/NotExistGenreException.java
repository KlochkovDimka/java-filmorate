package ru.yandex.practicum.filmorate.exceptions;

public class NotExistGenreException extends RuntimeException {

    public NotExistGenreException(String message) {
        super(message);
    }
}
