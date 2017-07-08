package am.ik.cf.metrics.forwarder;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import am.ik.cf.metrics.prometheus.PrometheusMetric;
import am.ik.cf.metrics.prometheus.PrometheusMetricRepository;
import am.ik.cf.metrics.security.MetricsAuthentication;

@RestController
public class MetricsForwarderController {
	private final PrometheusMetricRepository prometheusMetricRepository;

	public MetricsForwarderController(
			PrometheusMetricRepository prometheusMetricRepository) {
		this.prometheusMetricRepository = prometheusMetricRepository;
	}

	@PostMapping("/")
	public void metrics(@RequestBody Payload payload,
			MetricsAuthentication authentication) {
		String spaceId = authentication.getSpaceId();
		payload.getApplications().forEach(application -> {
			String applicationId = application.getId();
			List<Instance> instances = application.getInstances();
			instances.forEach(instance -> {
				String instanceId = instance.getId();
				String instanceIndex = instance.getIndex();
				List<PrometheusMetric> metricList = instance.getMetrics()
						.stream().map(metric -> new PrometheusMetric(applicationId,
								instanceIndex, instanceId, metric))
						.collect(Collectors.toList());
				this.prometheusMetricRepository.save(spaceId, metricList);
			});
		});
	}
}
