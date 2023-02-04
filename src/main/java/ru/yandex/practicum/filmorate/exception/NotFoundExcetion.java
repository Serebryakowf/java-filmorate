package ru.yandex.practicum.filmorate.exception;

public class NotFoundExcetion extends RuntimeException{
    public NotFoundExcetion(String message) {
        super(message);
    }
}
