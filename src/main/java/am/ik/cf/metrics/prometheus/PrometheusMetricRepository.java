package am.ik.cf.metrics.prometheus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import am.ik.cf.metrics.forwarder.Metric;
import am.ik.cf.metrics.forwarder.Type;

@Repository
public class PrometheusMetricRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public PrometheusMetricRepository(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public void save(String spaceId, List<PrometheusMetric> metricList) {
		SqlParameterSource[] parameterSources = metricList.stream()
				.map(metric -> new MapSqlParameterSource()
						.addValue("name", metric.metric.getName()) //
						.addValue("timestamp",
								new Timestamp(metric.metric.getTimestamp())) //
						.addValue("type", metric.metric.getType().getValue()) //
						.addValue("unit", Objects.toString(metric.metric.getUnit(), "")) //
						.addValue("value", metric.metric.getValue()) //
						.addValue("application_id", metric.applicationId) //
						.addValue("application_instance_index",
								metric.applicationInstanceIndex) //
						.addValue("application_instance_id", metric.applicationInstanceId) //
						.addValue("space_id", spaceId))
				.toArray(SqlParameterSource[]::new);
		jdbcTemplate.batchUpdate(
				"INSERT INTO metric(`name`, `timestamp`, `type`, `unit`, `value`, `application_id`, `application_instance_index`, `application_instance_id`, `space_id`) VALUES (:name, :timestamp, :type, :unit, :value, :application_id, :application_instance_index, :application_instance_id, :space_id) ON DUPLICATE KEY UPDATE `timestamp` = :timestamp, `value` = :value",
				parameterSources);
	}

	public PrometheusMetrics findBySpaceId(String spaceId) {
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("space_id", spaceId);
		return new PrometheusMetrics(jdbcTemplate.query(
				"SELECT `name`, `timestamp`, `type`, `unit`, `value`, `application_id`, `application_instance_index`, `application_instance_id`, `space_id` FROM metric WHERE space_id = :space_id ORDER BY `name`, `application_id`, `application_instance_index`",
				parameterSource, (rs, rowNum) -> {
					Metric metric = new Metric(rs.getString("name"),
							rs.getTimestamp("timestamp").getTime(),
							Type.valueOf(rs.getString("type").toUpperCase()),
							rs.getString("unit"), rs.getFloat("value"));
					return new PrometheusMetric(rs.getString("application_id"),
							rs.getString("application_instance_index"),
							rs.getString("application_instance_id"), metric);
				}));
	}

	@Transactional
	@Scheduled(cron = "${metric-forwarder-service.cleanup.cron.expression:0 * * * * *}")
	public void cleanUpExpiredMetrics() {
		LocalDateTime expiration = LocalDateTime.now().minusDays(3);
		SqlParameterSource parameterSource = new MapSqlParameterSource()
				.addValue("expiration", Timestamp.valueOf(expiration));
		jdbcTemplate.update("DELETE FROM metric WHERE `timestamp` < :expiration",
				parameterSource);
	}

}
