package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundExcetion;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage{
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film create(Film film) {
        film.setId(getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getList() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> getById(int id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public Film removeById(int id) {
        if (!films.containsKey(id)) {
            throw new NotFoundExcetion(String.format("Фильма с id %d не существует", id));
        }
        return films.remove(id);
    }

    @Override
    public List<Film> getTopFilms(int count) {
        return getList().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getLikes().size(), p1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    private int getId() {
        return ++id;
    }
}
