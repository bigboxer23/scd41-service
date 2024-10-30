package com.bigboxer23.scd41;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** */
@RestController
public class WebController {
	private static final Logger logger = LoggerFactory.getLogger(WebController.class);

	private SCD41Component scd41Component;

	public WebController(SCD41Component scd41Component) {
		this.scd41Component = scd41Component;
	}

	@GetMapping(path = "/data", produces = "application/json;charset=UTF-8")
	public Map<String, Float> getAveragedData() {
		logger.debug("Data requested");
		return scd41Component.getAveragedData();
	}
}
