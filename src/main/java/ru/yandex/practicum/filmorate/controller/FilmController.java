package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmsService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmsService filmsService;

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        log.info("Creating Film {}", film);
        return filmsService.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        log.info("Updating Film {}", film);
        return filmsService.update(film);
    }

    @GetMapping
    public List<Film> getList() {
        log.info("Getting all films");
        return filmsService.getList();
    }

    @GetMapping("/{id}")
    public Film getById(@PathVariable Integer id) {
        log.info("Getting Film by id");
        return filmsService.getById(id);
    }

    @DeleteMapping("/{id}")
    public Film removeById(@PathVariable Integer id) {
        log.info("Removing Film by id");
        return filmsService.removeById(id);
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
    public List<Film> getTopFilms(@Positive @RequestParam(defaultValue = "10") Integer count) {
        log.info("Getting top films");
        return filmsService.getTopFilms(count);
    }

}
