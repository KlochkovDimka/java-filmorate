package ru.yandex.practicum.filmorate.exceptions;

public class NotExistFilmException extends RuntimeException {

    public NotExistFilmException(String message) {
        super(message);
    }
}
