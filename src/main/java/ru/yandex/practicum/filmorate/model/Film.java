package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;

    @NotBlank
    private final String name;

    @NotBlank
    @Size(max = 200)
    private final String description;

    @NotNull
    private final LocalDate releaseDate;

    @Positive
    private final int duration;

    private final Set<Integer> likes = new HashSet<>();

}
