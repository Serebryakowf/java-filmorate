package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.NotFoundExcetion;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmsServiceTest {
    private FilmsService filmsService;

    @BeforeEach
    public void createFilmsService() {
        FilmStorage filmStorage = new InMemoryFilmStorage();
        UserStorage userStorage = new InMemoryUserStorage();
        UserService userService= new UserService(userStorage);
        this.filmsService = new FilmsService(filmStorage, userService);
    }

    @Test
    public void shouldThrowValidationException() {
        final ValidationException ex = assertThrows(
                ValidationException.class, () -> filmsService.create(createOldFilm()));
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", ex.getMessage());

        Film film = createNewFilm();
        film.setId(500);
        final NotFoundExcetion ex1 = assertThrows(
                NotFoundExcetion.class,
                () -> filmsService.update(film));
        assertEquals(String.format("Фильма с id %d не существует", film.getId()), ex1.getMessage());
    }

    private Film createOldFilm() {
        return new Film("Фильм", "Новый",
                LocalDate.of(1890, Month.DECEMBER, 28), 120);
    }

    private Film createNewFilm() {
        return new Film("Фильм", "Новый",
                LocalDate.of(2023, Month.JANUARY, 1), 120);
    }

}
