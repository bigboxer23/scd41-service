package com.bigboxer23.scd41;

import java.util.ArrayList;
import java.util.List;

/** */
public interface ISCD41Constants {
	String PYTHON_FILE_NAME = "read-sensor.py";

	/** Data we get readings from the sensor about */
	List<String> SENSOR_DATA = new ArrayList<>() {
		{
			add("CO2");
			add("Temperature");
			add("humidity");
		}
	};
}