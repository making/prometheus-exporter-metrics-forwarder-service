package am.ik.cf.metrics.prometheus;

import am.ik.cf.metrics.forwarder.Metric;

public class PrometheusMetric {
	final String applicationId;
	final String applicationInstanceIndex;
	final String applicationInstanceId;
	final Metric metric;

	public PrometheusMetric(String applicationId, String applicationInstanceIndex,
			String applicationInstanceId, Metric metric) {
		this.applicationId = applicationId;
		this.applicationInstanceIndex = applicationInstanceIndex;
		this.applicationInstanceId = applicationInstanceId;
		this.metric = metric;
	}

	public String toString(String applicationName) {
		StringBuilder builder = new StringBuilder();
		String metricName = this.metric.metricName();
		builder.append(metricName) //
				.append("{") //
				.append("application_id=\"") //
				.append(applicationId).append("\",") //
				.append("application_instance_index=\"") //
				.append(applicationInstanceIndex) //
				.append("\",") //
				.append("application_instance_id=\"") //
				.append(applicationInstanceId) //
				.append("\",") //
				.append("application_name=\"") //
				.append(applicationName) //
				.append("\",") //
				.append("} ") //
				.append(this.metric.getValue());
		if (!metric.getName().startsWith("counter.")) {
			builder.append(" ") //
					.append(this.metric.getTimestamp());
		}
		builder.append(System.lineSeparator());
		return builder.toString();
	}
}
