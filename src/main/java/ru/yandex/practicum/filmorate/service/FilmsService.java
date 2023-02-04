package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundExcetion;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmsService {
    private static final LocalDate BEGINNING_OF_THE_CINEMA_ERA = LocalDate.of(1895, Month.DECEMBER, 28);
    private final FilmStorage filmStorage;
    private final UserService usersService;

    public Film create(Film film) {
        checkReleaseDate(film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        getById(film.getId());
        checkReleaseDate(film);
        return filmStorage.update(film);
    }

    public List<Film> getList() {
        return filmStorage.getList();
    }

    public Film getById(int id) {
        return filmStorage.getById(id).orElseThrow(() ->
                new NotFoundExcetion(String.format("Фильма с id %d не существует", id))
        );
    }

    public Film removeById(int id) {
        return filmStorage.removeById(id);
    }

    public Film addLike(int userId, int filmId) {
        usersService.getById(userId);
        Film film = getById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int userId, int filmId) {
        usersService.getById(userId);
        Film film = getById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getTopFilms(int count) {
        return filmStorage.getTopFilms(count);
    }

    private void checkReleaseDate(Film film) {
        if (film.getReleaseDate().isBefore(BEGINNING_OF_THE_CINEMA_ERA)) {
            throw new ValidationException("Дата релиза не может быть раньше 28 декабря 1895 года");
        }
    }
}
