package com.bigboxer23.scd41;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SCD41App {
	public static void main(String[] args) {
		SpringApplication.run(SCD41App.class, args);
	}

	public SCD41App() {}
}
