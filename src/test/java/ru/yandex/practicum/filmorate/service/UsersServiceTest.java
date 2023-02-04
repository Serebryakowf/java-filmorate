package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundExcetion;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsersServiceTest {
    private UserService usersService;

    @BeforeEach
    public void createUsersService() {
        this.usersService = new UserService(new InMemoryUserStorage());
    }

    @Test
    public void shouldThrowValidationException() {
        User user = new User("123@gmail.com", "ab c", LocalDate.of(2023, Month.JANUARY, 1));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> usersService.create(user));
        assertEquals("Недопустимы пробелы в login", exception.getMessage());

        User user1 = new User("123@gmail.com", "abc", LocalDate.of(2023, Month.JANUARY, 1));
        user.setId(500);
        final NotFoundExcetion exception1 = assertThrows(
                NotFoundExcetion.class, () -> usersService.update(user1));
        assertEquals(String.format("Пользователя с id %d не существует", user1.getId()), exception1.getMessage());
    }


}
