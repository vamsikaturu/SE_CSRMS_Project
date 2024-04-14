package com.pack.seproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.pack.seproject")
@EnableScheduling
public class SeprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeprojectApplication.class, args);
	}

}
