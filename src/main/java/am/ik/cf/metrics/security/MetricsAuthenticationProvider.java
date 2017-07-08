package am.ik.cf.metrics.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class MetricsAuthenticationProvider implements AuthenticationProvider {
	private final MetricsTokenRepository metricsTokenRepository;

	public MetricsAuthenticationProvider(MetricsTokenRepository metricsTokenRepository) {
		this.metricsTokenRepository = metricsTokenRepository;
	}

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		String token = (String) authentication.getCredentials();
		return this.metricsTokenRepository.spaceIdByToken(token)
				.map(spaceId -> (Authentication) new MetricsAuthentication(spaceId))
				.orElseThrow(() -> new BadCredentialsException("token is invalid."));
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return MetricsToken.class.equals(authentication);
	}
}
