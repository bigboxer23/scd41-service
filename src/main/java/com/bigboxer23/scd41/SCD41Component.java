package com.bigboxer23.scd41;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
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

	public SCD41Component() {
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
		String[] content = Optional.ofNullable(data).map(d -> d.split(":")).orElse(new String[0]); // data.split(",");
		if (content.length != 3) {
			logger.error("Data read from processor is invalid: " + data);
			return;
		}
		logger.debug("Sensor Data: " + data);
	}
}
