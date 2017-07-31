package am.ik.cf.metrics.capi;

import java.util.function.Consumer;

import org.cloudfoundry.client.CloudFoundryClient;
import org.cloudfoundry.client.v2.applications.ListApplicationsRequest;
import org.cloudfoundry.client.v2.applications.ListApplicationsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "cf.api-host")
public class CloudControllerPollingScheduler
		implements Consumer<ListApplicationsResponse> {
	private static final Logger log = LoggerFactory
			.getLogger(CloudControllerPollingScheduler.class);
	private final CloudFoundryClient cloudFoundryClient;
	private final Cache cache;

	public CloudControllerPollingScheduler(CloudFoundryClient cloudFoundryClient,
			CacheManager cacheManager) {
		this.cloudFoundryClient = cloudFoundryClient;
		this.cache = cacheManager.getCache("cf-applications");
	}

	@Override
	public void accept(ListApplicationsResponse response) {
		log.debug("Handling response from CC", response);
		response.getResources().forEach(app -> {
			String id = app.getMetadata().getId();
			String name = app.getEntity().getName();
			this.cache.put(id, name);
		});
	}

	@Scheduled(fixedRateString = "${metric-forwarder-service.cc-polling-millis:60000}")
	public void pullFromCc() {
		log.debug("Pulling from CC");
		this.cloudFoundryClient.applicationsV2() //
				.list(ListApplicationsRequest.builder().build()) //
				.doOnSuccess(this) //
				.subscribe();
	}
}
