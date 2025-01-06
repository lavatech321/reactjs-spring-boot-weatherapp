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
    public Weather createWeather(@RequestBody Weather w1) {
        return weatherRepository.save(w1);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteWeather(@PathVariable Long id) {
        Weather w1 = weatherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Weather Record does not exists"));
        weatherRepository.deleteById(id);
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }
}
