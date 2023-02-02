package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmsService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmsService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public Film createFilm(Film film) {
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getFilms() {
        return new ArrayList<>(filmStorage.getFilms().values());
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public Film removeFilmById(int id) {
        return filmStorage.removeFilmById(id);
    }

    public Film addLike(int userId, int filmId) {
        Film film = getFilmById(filmId);
        film.getLikes().add(userId);
        return film;
    }

    public Film removeLike(int userId, int filmId) {
        Film film = getFilmById(filmId);
        film.getLikes().remove(userId);
        return film;
    }

    public List<Film> getTopFilms(int count) {
        return getFilms().stream()
                .sorted(Comparator.comparingInt(p0 -> p0.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
