package com.lavatech.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Welcome {
    @GetMapping("/welcome")
    public String display() {
        return "Hello world";
    }
}
