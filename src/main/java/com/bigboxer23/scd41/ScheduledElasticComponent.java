package com.bigboxer23.scd41;

import static com.bigboxer23.scd41.ISCD41Constants.ELASTIC_INDEX;
import static com.bigboxer23.scd41.ISCD41Constants.ELASTIC_TYPE;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** */
@Component
public class ScheduledElasticComponent implements DisposableBean {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledElasticComponent.class);

	private final SCD41Component scd41Component;

	@Value("${elastic.url}")
	private String ELASTIC_URL;

	private RestHighLevelClient client;

	public ScheduledElasticComponent(SCD41Component scd41Component) {
		this.scd41Component = scd41Component;
	}

	@Scheduled(fixedDelay = 60000)
	private void runScheduledCheck() {
		logger.debug("sending data");
		sendToElastic(new HashMap<>(scd41Component.getAveragedData()));
	}

	protected void sendToElastic(Map<String, Object> data) {
		if (data.isEmpty()) {
			logger.warn("Not sending anything to elastic, empty data set.");
			return;
		}
		BulkRequest request = new BulkRequest();
		data.put("time", new Date());
		data.put("sensor", "scd41");
		request.add(new IndexRequest(ELASTIC_INDEX, ELASTIC_TYPE, "boom" + System.currentTimeMillis()).source(data));
		logger.debug("Sending Request to elastic");
		try {
			BulkResponse response = getClient().bulk(request, RequestOptions.DEFAULT);
			if (response.hasFailures()) {
				logger.error(response.buildFailureMessage());
			}
		} catch (IOException e) {
			logger.debug("sendToElastic", e);
		}
	}

	@Override
	public void destroy() throws IOException {
		if (client != null) {
			logger.debug("closing elastic client");
			client.close();
		}
	}

	/**
	 * I'd rather not use the RHLC, but there isn't an elastic 6x compat version of the updated
	 * elastic client library
	 *
	 * @return
	 */
	private RestHighLevelClient getClient() {
		if (client == null) {
			client = new RestHighLevelClient(RestClient.builder(HttpHost.create(ELASTIC_URL)));
		}
		return client;
	}
}
