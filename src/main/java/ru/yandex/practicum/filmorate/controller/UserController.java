package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UsersService;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        log.debug("Creating User {}", user);
        return usersService.createUser(user);
    }

    @PutMapping
    public User updateUser(@Valid @RequestBody User user) {
        log.info("Updating User {}", user);
        return usersService.updateUser(user);
    }

    @GetMapping
    public List<User> getUsers() {
        log.info("Getting all users");
        return usersService.getUsers();
    }
}
