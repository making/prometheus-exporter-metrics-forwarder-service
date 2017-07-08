package am.ik.cf.metrics.servicebroker;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ServiceInstanceRepository {
	private final JdbcTemplate jdbcTemplate;

	public ServiceInstanceRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<String> spaceIdByServiceInstanceId(String serviceInstanceId) {
		try {
			String spaceId = this.jdbcTemplate.queryForObject(
					"SELECT space_id FROM service_instance WHERE service_instance_id = ?",
					String.class, serviceInstanceId);
			return Optional.of(spaceId);
		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void save(String serviceInstanceId, String spaceId) {
		this.jdbcTemplate.update(
				"INSERT INTO service_instance(service_instance_id, space_id) VALUE (?, ?)",
				serviceInstanceId, spaceId);
	}

	public void delete(String serviceInstanceId) {
		this.jdbcTemplate.update(
				"DELETE FROM service_instance WHERE service_instance_id = ?",
				serviceInstanceId);
	}
}
