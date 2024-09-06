package com.eve.events;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@SpringBootApplication
public class EventsApplication {

	public static void main(String[] args) {
		log.info("Starting EventsHub Application!");
		SpringApplication.run(EventsApplication.class, args);
		log.info("EventsHub Application started successfully");
	}

	@Bean
	public ModelMapper modelMapper() {
		log.info("Creating ModelMapper bean");
		return new ModelMapper();
	}
}
