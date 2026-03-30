package com.example.moattravel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class Moattravel2Application {

	public static void main(String[] args) {
		SpringApplication.run(Moattravel2Application.class, args);
	}

}
