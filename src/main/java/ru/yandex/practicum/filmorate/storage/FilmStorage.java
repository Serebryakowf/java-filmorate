package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {

    Film create(Film film);

    Film update(Film film);

    List<Film> getList();

    Optional<Film> getById(int id);

    Film removeById(int id);

    List<Film> getTopFilms(int count);
}
