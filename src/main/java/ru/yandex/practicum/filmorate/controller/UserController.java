package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService usersService;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.debug("Creating User {}", user);
        return usersService.create(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Updating User {}", user);
        return usersService.update(user);
    }

    @GetMapping
    public List<User> getList() {
        log.info("Getting all users");
        return usersService.getList();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public List<Integer> addFriend(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Adding friends");
        return usersService.addFriend(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public List<Integer> removeFromFriends(@PathVariable Integer id, @PathVariable Integer friendId) {
        log.info("Removing from friends");
        return usersService.removeFromFriends(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) {
        log.info("Getting friends");
        return usersService.getFriends(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable Integer id, @PathVariable Integer otherId) {
        log.info("Getting common friends");
        return usersService.getCommonFriends(id, otherId);
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable Integer id) {
        log.info("Getting User by id");
        return usersService.getById(id);
    }

    @DeleteMapping("/{id}")
    public User removeById(@PathVariable Integer id) {
        log.info("Removing User by id");
        return usersService.removeById(id);
    }
}
