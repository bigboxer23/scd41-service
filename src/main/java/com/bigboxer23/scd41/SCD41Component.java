package com.bigboxer23.scd41;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.zeroturnaround.exec.ProcessExecutor;
import org.zeroturnaround.exec.stream.LogOutputStream;

/** */
@Component
public class SCD41Component implements ISCD41Constants {
	private static final Logger logger = LoggerFactory.getLogger(SCD41Component.class);

	private String path = null;

	private final Cache<Long, Map<String, Float>> cache;

	public SCD41Component() {
		cache = CacheBuilder.newBuilder().maximumSize(30).build();
		initPythonScript();
		startSensorProcess();
	}

	private void initPythonScript() {
		File pythonFile = new File(System.getProperty("user.dir") + File.separator + PYTHON_FILE_NAME);
		try {
			FileUtils.copyInputStreamToFile(
					Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(PYTHON_FILE_NAME)),
					pythonFile);
		} catch (IOException e) {
			logger.error("initPythonScript", e);
		}
		path = pythonFile.getAbsolutePath();
	}

	private void startSensorProcess() {
		try {
			new ProcessExecutor()
					.command("python3", "-u", path)
					.redirectOutput(new LogOutputStream() {
						@Override
						protected void processLine(String theLine) {
							readSensorData(theLine);
						}
					})
					.destroyOnExit()
					.start();
		} catch (IOException e) {
			logger.error("startSensorProcess ", e);
		}
	}

	private void readSensorData(String data) {
		String[] content = Optional.ofNullable(data).map(d -> d.split(":")).orElse(new String[0]);
		if (content.length != 3) {
			logger.error("Data read from processor is invalid: " + data);
			return;
		}
		logger.debug("Sensor Data: " + data);
		Map<String, Float> transformed = new HashMap<>();
		cache.put(System.currentTimeMillis(), transformed);
		Integer[] index = {0};
		SENSOR_DATA.forEach(k -> {
			transformed.put(k, transformData(content[index[0]], k));
			index[0]++;
		});
	}

	private float transformData(String data, String key) {
		float transformed = Float.parseFloat(data);
		if ("temperature".equals(key)) {
			return celciusToFahrenheit(transformed);
		}
		return transformed;
	}

	private float celciusToFahrenheit(float celcius) {
		return (celcius * 1.8f) + 32f;
	}

	public Map<String, Float> getAveragedData() {
		Map<String, Float> averages = new HashMap<>();
		if (cache.size() == 0) {
			return averages;
		}
		// Loop through all maps in cache and accumulate the values for each sensor
		cache.asMap()
				.values()
				.forEach(map -> SENSOR_DATA.forEach(
						item -> averages.put(item, averages.getOrDefault(item, 0f) + map.get(item))));

		// After accumulating, calculate the average by dividing by the size of the cache
		SENSOR_DATA.forEach(item -> averages.put(item, averages.getOrDefault(item, 0f) / cache.size()));
		return averages;
	}
}
