package com.global.volunteer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class GlobalvolunteerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GlobalvolunteerApplication.class, args);
		log.info("== Application started on port == 6060 ");
	}

}
