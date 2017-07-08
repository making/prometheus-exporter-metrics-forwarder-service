package am.ik.cf.metrics.prometheus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import am.ik.cf.metrics.security.MetricsAuthentication;

@RestController
public class PrometheusExporter {
	private final PrometheusMetricRepository prometheusMetricRepository;

	public PrometheusExporter(PrometheusMetricRepository prometheusMetricRepository) {
		this.prometheusMetricRepository = prometheusMetricRepository;
	}

	@GetMapping(path = "/prometheus", produces = "text/plain; version=0.0.4")
	public String export(MetricsAuthentication authentication) {
		String spaceId = authentication.getSpaceId();
		PrometheusMetrics prometheusMetrics = this.prometheusMetricRepository
				.findBySpaceId(spaceId);
		return prometheusMetrics.toString();
	}
}
