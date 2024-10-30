package com.bigboxer23.scd41;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@OpenAPIDefinition(
		info =
				@Info(
						title = "SCD41 Service",
						version = "1",
						description = "An averaged reading of CO2, temperature (C), and relative humidity"
								+ " (%) from a SCD41 (https://www.adafruit.com/product/5190) ",
						contact =
								@Contact(
										name = "bigboxer23@gmail.com",
										url = "https://github.com/bigboxer23/scd41-service")))
public class SCD41App {
	public static void main(String[] args) {
		SpringApplication.run(SCD41App.class, args);
	}

	public SCD41App() {}
}
