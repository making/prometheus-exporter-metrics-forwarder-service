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

	@Override
	public String toString() {
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
				.append("} ") //
				.append(this.metric.getValue()) //
				.append(" ") //
				.append(this.metric.getTimestamp()) //
				.append(System.lineSeparator());
		return builder.toString();
	}
}
