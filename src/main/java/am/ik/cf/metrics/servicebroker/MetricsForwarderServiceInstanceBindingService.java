package am.ik.cf.metrics.servicebroker;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import am.ik.cf.metrics.security.MetricsTokenRepository;

@Component
@Transactional
public class MetricsForwarderServiceInstanceBindingService
		implements ServiceInstanceBindingService {
	private final String externalUrl;
	private final ServiceInstanceRepository serviceInstanceRepository;
	private final MetricsTokenRepository metricsTokenRepository;

	public MetricsForwarderServiceInstanceBindingService(
			@Value("${metric-forwarder-service.external-url:}") String externalUrl,
			ServiceInstanceRepository serviceInstanceRepository,
			MetricsTokenRepository metricsTokenRepository) {
		this.externalUrl = externalUrl;
		this.serviceInstanceRepository = serviceInstanceRepository;
		this.metricsTokenRepository = metricsTokenRepository;
	}

	@Override
	public CreateServiceInstanceBindingResponse createServiceInstanceBinding(
			CreateServiceInstanceBindingRequest request) {
		String spaceId = this.serviceInstanceRepository
				.spaceIdByServiceInstanceId(request.getServiceInstanceId())
				.orElseThrow(() -> new IllegalArgumentException(
						"The requested service instance does not exist!"));
		String token = UUID.randomUUID().toString();
		this.metricsTokenRepository.save(token, spaceId, request.getBindingId());

		Map<String, Object> credentials = new LinkedHashMap<>();
		credentials.put("access_key", "'Bearer " + token + "'");
		credentials.put("endpoint", externalUrl);
		credentials.put("prometheus_exporter", externalUrl + "/prometheus");

		return new CreateServiceInstanceAppBindingResponse().withCredentials(credentials);
	}

	@Override
	public void deleteServiceInstanceBinding(
			DeleteServiceInstanceBindingRequest request) {
		this.metricsTokenRepository.deleteByBindingId(request.getBindingId());
	}
}
