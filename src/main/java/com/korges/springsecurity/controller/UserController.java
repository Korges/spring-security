package com.korges.springsecurity.controller;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @GetMapping("/{userId}")
    public String getUser(@PathVariable Integer userId) {
        return "User:" + userId;
    }

    @GetMapping
    public List<String> getUsers() {
        return Arrays.asList("User:1", "User:2", "User:3");
    }

    @PostMapping
    public String postUser(@RequestBody String user) {
        return "Post new User: " + user;
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable Integer userId) {
        return "User deleted:" + userId;
    }

    @PutMapping("/{userId}")
    public String updateUser(@PathVariable Integer userId, @RequestBody String user) {
        return user + userId;
    }
}
