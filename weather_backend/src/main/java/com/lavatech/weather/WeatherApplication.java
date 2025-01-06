package com.lavatech.weather;

import com.lavatech.weather.model.Weather;
import com.lavatech.weather.repository.WeatherRepository;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WeatherApplication implements CommandLineRunner {

	@Autowired
	WeatherRepository weatherRepository;

	public static void main(String[] args) {
		SpringApplication.run(WeatherApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Weather w1 = new Weather();
		String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=Pune&appid=029db852173d8ee15ccb004dffc4586c";
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject(API_URL, String.class);
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(response);
			String cityName = root.path("name").asText();
			double temperature = root.path("main").path("temp").asDouble();
			double temperatureInCelsius = temperature - 273.15;
			String icon = root.path("weather").get(0).path("icon").asText();
			System.out.println(String.format("City: %s, Temperature: %.2f, Icon: %s", cityName, temperatureInCelsius, icon));

			// test entering record in table
			w1.setCity(cityName);
			weatherRepository.save(w1);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
