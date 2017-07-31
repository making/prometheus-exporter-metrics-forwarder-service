package am.ik.cf.metrics.capi;

import org.cloudfoundry.reactor.ConnectionContext;
import org.cloudfoundry.reactor.DefaultConnectionContext;
import org.cloudfoundry.reactor.TokenProvider;
import org.cloudfoundry.reactor.client.ReactorCloudFoundryClient;
import org.cloudfoundry.reactor.tokenprovider.PasswordGrantTokenProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cf")
@ConditionalOnProperty(name = "cf.api-host")
public class CloudFoundryConfig {

	private String apiHost;
	private boolean skipSslValidation = false;
	private String username;
	private String password;

	@Bean
	DefaultConnectionContext connectionContext() {
		return DefaultConnectionContext.builder().apiHost(this.apiHost)
				.skipSslValidation(this.skipSslValidation).build();
	}

	@Bean
	PasswordGrantTokenProvider tokenProvider() {
		return PasswordGrantTokenProvider.builder().password(this.password)
				.username(this.username).build();
	}

	@Bean
	ReactorCloudFoundryClient cloudFoundryClient(ConnectionContext connectionContext,
			TokenProvider tokenProvider) {
		return ReactorCloudFoundryClient.builder().connectionContext(connectionContext)
				.tokenProvider(tokenProvider).build();
	}

	public String getApiHost() {
		return apiHost;
	}

	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}

	public boolean isSkipSslValidation() {
		return skipSslValidation;
	}

	public void setSkipSslValidation(boolean skipSslValidation) {
		this.skipSslValidation = skipSslValidation;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
