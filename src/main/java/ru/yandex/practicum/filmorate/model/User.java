package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private int id;

    @NotEmpty
    @Email
    private final String email;

    @NotBlank
    private final String login;

    private String name;

    @NotNull
    @PastOrPresent
    private final LocalDate birthday;

    @JsonIgnore
    private final Set<Integer> friends = new HashSet<>();
}
