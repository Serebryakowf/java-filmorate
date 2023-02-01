package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UsersServiceTest {
    private UsersService usersService;

    @BeforeEach
    public void createUsersService() {
        this.usersService = new UsersService();
    }

    @Test
    public void shouldThrowValidationException() {
        User user = new User("123@gmail.com", "ab c", LocalDate.of(2023, Month.JANUARY, 1));
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> usersService.createUser(user));
        assertEquals("Недопустимы пробелы в login", exception.getMessage());

        User user1 = new User("123@gmail.com", "abc", LocalDate.of(2023, Month.JANUARY, 1));
        user.setId(500);
        final ValidationException exception1 = assertThrows(
                ValidationException.class, () -> usersService.updateUser(user1));
        assertEquals("User с данным id отсутствует", exception1.getMessage());
    }


}
