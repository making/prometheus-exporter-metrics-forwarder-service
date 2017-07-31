package am.ik.cf.metrics.prometheus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.cache.Cache;

public class PrometheusMetrics {
	private final List<PrometheusMetric> metrics;
	private final Cache cache;

	public PrometheusMetrics(List<PrometheusMetric> metrics, Cache cache) {
		this.metrics = metrics;
		this.cache = cache;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		Map<String, List<PrometheusMetric>> map = this.metrics.stream()
				.collect(Collectors.groupingBy(x -> x.metric.metricName()));
		map.forEach((metricName, metrics) -> {
			String type = metricName.contains("counter") ? "counter" : "gauge";
			builder.append("# HELP ").append(metricName).append(System.lineSeparator());
			builder.append("# TYPE ").append(metricName).append(" ").append(type)
					.append(System.lineSeparator());
			metrics.forEach(metric -> {
				String applicationName = cache.get(metric.applicationId, () -> "N/A");
				builder.append(metric.toString(applicationName));
				builder.append(System.lineSeparator());
			});
		});
		return builder.toString();
	}
}
