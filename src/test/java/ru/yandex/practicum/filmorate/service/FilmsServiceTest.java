package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmsServiceTest {
    private FilmsService filmsService;

    @BeforeEach
    public void createFilmsService() {
        this.filmsService = new FilmsService(new InMemoryFilmStorage());
    }

    @Test
    public void shouldThrowValidationException() {
        final ValidationException exception = assertThrows(
                ValidationException.class, () -> filmsService.createFilm(createOldFilm()));
        assertEquals("Дата релиза не может быть раньше 28 декабря 1895 года", exception.getMessage());

        Film film = createNewFilm();
        film.setId(500);
        final ValidationException exception1 = assertThrows(
                ValidationException.class,
                () -> filmsService.updateFilm(film));
        assertEquals("Film с данным id отсутствует", exception1.getMessage());
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
