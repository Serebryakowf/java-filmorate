package ru.yandex.practicum.filmorate.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FilmsService {
    private static final LocalDate BEGINNING_OF_THE_CINEMA_ERA = LocalDate.of(1895, Month.DECEMBER, 28);
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    public Film createFilm(Film film) {
        checkReleaseDate(film);
        film.setId(getId());
        films.put(film.getId(), film);
        return film;
    }

    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film с данным id отсутствует");
        }
        checkReleaseDate(film);
        films.put(film.getId(), film);
        return film;
    }

    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }
    private int getId() {
        return ++id;
    }

    private void checkReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(BEGINNING_OF_THE_CINEMA_ERA)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }

}
