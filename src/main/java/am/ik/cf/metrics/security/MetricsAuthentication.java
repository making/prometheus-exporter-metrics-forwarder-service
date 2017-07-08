package am.ik.cf.metrics.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class MetricsAuthentication implements Authentication {
	private final String spaceId;

	public MetricsAuthentication(String spaceId) {
		this.spaceId = spaceId;
	}

	public String getSpaceId() {
		return spaceId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.emptyList();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return spaceId;
	}

	@Override
	public Object getPrincipal() {
		return spaceId;
	}

	@Override
	public boolean isAuthenticated() {
		return true;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated)
			throws IllegalArgumentException {

	}

	@Override
	public String getName() {
		return spaceId;
	}

	@Override
	public String toString() {
		return "MetricsAuthentication{" + "spaceId='" + spaceId + '\'' + '}';
	}
}
