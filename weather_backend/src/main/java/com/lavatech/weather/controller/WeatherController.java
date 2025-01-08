package com.lavatech.weather.controller;

import com.lavatech.weather.exception.ResourceNotFoundException;
import com.lavatech.weather.model.Weather;
import com.lavatech.weather.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    WeatherRepository weatherRepository;

    @GetMapping
    public List<Weather> getAllWeather() {
        return weatherRepository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Weather> getWeather(@PathVariable Long id) {
        Weather w1 = weatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weather Record does not exists"));
        return ResponseEntity.ok(w1);
    }

    @PostMapping
    public ResponseEntity<String> createWeather(@RequestBody Weather w1) {
        // Check if the city already exists
        if (weatherRepository.existsByCity(w1.getCity())) {
            // If the city already exists, return a message saying the city already exists
            return ResponseEntity.status(400).body("City already exists");
        }

        // Save the new weather record
        weatherRepository.save(w1);

        return ResponseEntity.status(201).body("City added successfully");
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteWeather(@PathVariable Long id) {
        Weather w1 = weatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weather Record does not exists"));
        weatherRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
