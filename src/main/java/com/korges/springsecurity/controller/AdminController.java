package com.korges.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/admin")
public class AdminController {

    @GetMapping("/{infoId}")
    public String getSecretInfo(@PathVariable Integer infoId) {
        return "SecretInfo:" + infoId;
    }

    @GetMapping
    public List<String> getSecretInfo() {
        return Arrays.asList("SecretInfo:1", "SecretInfo:2", "SecretInfo:3");
    }
}
