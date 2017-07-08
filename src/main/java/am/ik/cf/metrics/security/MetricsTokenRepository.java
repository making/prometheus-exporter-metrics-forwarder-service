package am.ik.cf.metrics.security;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MetricsTokenRepository {
	private final JdbcTemplate jdbcTemplate;

	public MetricsTokenRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Optional<String> spaceIdByToken(String token) {
		try {
			String spaceId = this.jdbcTemplate.queryForObject(
					"SELECT space_id FROM token WHERE token = ?", String.class, token);
			return Optional.of(spaceId);
		}
		catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public void save(String token, String spaceId, String bindingId) {
		this.jdbcTemplate.update(
				"INSERT INTO token(token, space_id, binding_id) VALUE (?, ?, ?)", token,
				spaceId, bindingId);
	}

	public void deleteByToken(String token) {
		this.jdbcTemplate.update("DELETE FROM token WHERE token = ?", token);
	}

	public void deleteByBindingId(String bindingId) {
		this.jdbcTemplate.update("DELETE FROM token WHERE binding_id = ?", bindingId);
	}
}
