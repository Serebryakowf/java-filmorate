package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmsService;

import javax.validation.Valid;
import java.util.List;

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

}
