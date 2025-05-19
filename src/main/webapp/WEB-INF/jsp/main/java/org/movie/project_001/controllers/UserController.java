package org.movie.project_001.controllers;

import org.movie.project_001.models.User;
import org.movie.project_001.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUsers() throws IOException {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) throws IOException {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User user) throws IOException {
        userService.addUser(user);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable UUID id, @RequestBody User updatedUser) throws IOException {
        userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) throws IOException {
        userService.deleteUser(id);
    }

}
