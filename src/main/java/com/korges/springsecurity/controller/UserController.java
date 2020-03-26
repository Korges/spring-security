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
    public void postUser(@RequestBody String user) {
        System.out.println("Post new User: " + user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        System.out.println("User deleted:" + userId);
    }

    @PutMapping
    public void updateUser(@PathVariable Integer userId, @RequestBody String user) {
        System.out.println(user + userId);
    }
}
