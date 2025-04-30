package com.both.testing_pilot_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/no-auth")
public class NoAuth {

    @GetMapping
    public String hello() {
        return "werdftgyhujiklasdfasdfasdfasdf";
    }
}
