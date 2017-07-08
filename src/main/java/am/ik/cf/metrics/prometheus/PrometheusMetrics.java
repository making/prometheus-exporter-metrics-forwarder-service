package am.ik.cf.metrics.prometheus;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrometheusMetrics {
	private final List<PrometheusMetric> metrics;

	public PrometheusMetrics(List<PrometheusMetric> metrics) {
		this.metrics = metrics;
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
				builder.append(metric.toString());
				builder.append(System.lineSeparator());
			});
		});
		return builder.toString();
	}
}
