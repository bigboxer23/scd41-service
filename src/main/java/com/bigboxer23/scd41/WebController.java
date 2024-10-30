package com.bigboxer23.scd41;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.net.HttpURLConnection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** */
@RestController
@Tag(
		name = "SCD41 Data service",
		description =
				"APIs available for fetching CO2, Temperature, and Relative Humidity from an" + " underlying sensor")
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	private SCD41Component scd41Component;

	public WebController(SCD41Component scd41Component) {
		this.scd41Component = scd41Component;
	}

	@GetMapping(path = "/data", produces = "application/json;charset=UTF-8")
	@Operation(
			summary = "Get data averaged over the last 30 seconds for CO2, Temperature, Relative" + " Humidity",
			description = "Map of data is returned (CO2 PPM, Temperature Celcius, Relative Humidity %)")
	@ApiResponses({
		@ApiResponse(responseCode = HttpURLConnection.HTTP_BAD_REQUEST + "", description = "Bad request"),
		@ApiResponse(responseCode = HttpURLConnection.HTTP_OK + "", description = "success")
	})
	public Map<String, Float> getAveragedData() {
		logger.debug("Data requested");
		return scd41Component.getAveragedData();
	}
}
