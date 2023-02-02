package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmsService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmsService filmsService;

    public FilmController(FilmsService filmsService) {
        this.filmsService = filmsService;
    }

    @PostMapping
    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Creating Film {}", film);
        return filmsService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        log.info("Updating Film {}", film);
        return filmsService.updateFilm(film);
    }

    @GetMapping
    public List<Film> getFilms() {
        log.info("Getting all films");
        return filmsService.getFilms();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        log.info("Getting Film by id");
        return filmsService.getFilmById(id);
    }

    @DeleteMapping("/{id}")
    public Film removeFilmById(@PathVariable Integer id) {
        log.info("Removing Film by id");
        return filmsService.removeFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable Integer userId, @PathVariable Integer id) {
        log.info("Adding a like");
        return filmsService.addLike(userId, id);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film removeLike(@PathVariable Integer userId, @PathVariable Integer id) {
        log.info("Removing a like");
        return filmsService.removeLike(userId, id);
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(required = false) Integer count) {
        log.info("Getting a top films");
        return filmsService.getTopFilms(Objects.requireNonNullElse(count, 10));
    }

}
