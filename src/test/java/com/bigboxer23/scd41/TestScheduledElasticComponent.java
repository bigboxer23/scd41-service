package com.bigboxer23.scd41;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/** */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestScheduledElasticComponent {

	@Autowired
	private ScheduledElasticComponent scheduledElasticComponent;

	@Test
	public void sendToElastic() {
		Map<String, Object> data = new HashMap<>();
		ISCD41Constants.SENSOR_DATA.forEach(k -> {
			data.put(k, 2f);
		});
		scheduledElasticComponent.sendToElastic(data);
	}
}
