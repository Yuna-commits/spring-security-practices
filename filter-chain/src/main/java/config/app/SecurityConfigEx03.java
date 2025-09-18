package config.app;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.session.DisableEncodeUrlFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfigEx03 {
	
	@Bean
	public FilterChainProxy springSecurityFilterChain() {
		List<SecurityFilterChain> securityFileterChains = Arrays.asList(
				// spring security 제공 DefaultSecurityFilterChain <- SecurityFilterChain 구현체
				new DefaultSecurityFilterChain(
						// /assets은 보안 필터 없음
						new AntPathRequestMatcher("/assets/**")),
				new DefaultSecurityFilterChain(
						// 모든 나머지 요청에는 custom filter 등록
						new AntPathRequestMatcher("/**"), disableEncodeUrlFilter(), webAsyncManagerIntegrationFilter(), defaultLoginPageGeneratingFilter()));

		return new FilterChainProxy(securityFileterChains);
	}

	/**
	 * Session ID가 URL에 포함되는 것을 막기 위해 URL 인코딩을 막는 필터
	 */
	@Bean
	public DisableEncodeUrlFilter disableEncodeUrlFilter() {
		return new DisableEncodeUrlFilter();
	}

	/**
	 * 비동기 관련 기능을 만들 때 security context 사용하게 해주는 필터
	 */
	@Bean
	public WebAsyncManagerIntegrationFilter webAsyncManagerIntegrationFilter() {
		return new WebAsyncManagerIntegrationFilter();
	}

	/**
	 * 로그인 기본 페이지 생성 필터
	 */
	@Bean
	public DefaultLoginPageGeneratingFilter defaultLoginPageGeneratingFilter() {
		return new DefaultLoginPageGeneratingFilter();
	}

}
