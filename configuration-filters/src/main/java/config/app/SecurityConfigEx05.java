package config.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigEx05 {
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return new WebSecurityCustomizer() {
			@Override
			public void customize(WebSecurity web) {
				web.ignoring().requestMatchers(new AntPathRequestMatcher("/assets/**"));
			}
		};
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.formLogin(configurer -> {configurer.loginPage("/user/login");})
			.authorizeHttpRequests(
					registry -> {/* Access Control List (ACL) */
						// url이 /board/(write|delete|modify|reply)이면 authUser만 접근 가능
						registry.requestMatchers(new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$", null)).authenticated()
						// 나머지 요청은 인증 없이 접근 가능
						.anyRequest().permitAll();
					});

		return http.build();
	}
}
