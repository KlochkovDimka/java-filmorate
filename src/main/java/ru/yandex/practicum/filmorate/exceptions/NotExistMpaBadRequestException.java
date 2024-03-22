package ru.yandex.practicum.filmorate.exceptions;

public class NotExistMpaBadRequestException extends RuntimeException {
    public NotExistMpaBadRequestException(String message) {
        super(message);
    }
}
