package am.ik.cf.metrics.security;

import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Order(-20)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private final MetricsAuthenticationProvider metricsAuthenticationProvider;

	public SecurityConfig(MetricsAuthenticationProvider metricsAuthenticationProvider) {
		this.metricsAuthenticationProvider = metricsAuthenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.requestMatchers() //
				.mvcMatchers("/", "/prometheus") //
				.and() //
				.authorizeRequests() //
				.anyRequest().authenticated() //
				.and() //
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //
				.and() //
				.csrf().disable() //
				.addFilterBefore(new MetricsTokenFilter(),
						BasicAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(metricsAuthenticationProvider);
	}
}
