package com.korges.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/students")
public class ApiController {

    @GetMapping("/{studentId}")
    public String getStudent(@PathVariable Integer studentId) {
        return "Student:" + studentId;
    }
}
