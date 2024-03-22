package ru.yandex.practicum.filmorate.exceptions;

public class NotExistMpaException extends RuntimeException {
    public NotExistMpaException(String message) {
        super(message);
    }
}
