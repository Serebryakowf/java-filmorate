package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ModelNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private static final LocalDate BEGINNING_OF_THE_CINEMA_ERA = LocalDate.of(1895, Month.DECEMBER, 28);
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film createFilm(Film film) {
        checkReleaseDate(film);
        film.setId(getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Film с данным id отсутствует");
        }
        checkReleaseDate(film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Map<Integer, Film> getFilms() {
        return films;
    }

    @Override
    public Film getFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new ModelNotFoundException(String.format("Фильма с id %d не существует", id));
        }
        return films.get(id);
    }

    @Override
    public Film removeFilmById(int id) {
        if (!films.containsKey(id)) {
            throw new ModelNotFoundException(String.format("Фильма с id %d не существует", id));
        }
        return films.remove(id);
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
