package config.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import config.WebConfig;
import jakarta.servlet.Filter;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WebConfig.class, SecurityConfigEx02.class})
@WebAppConfiguration
public class SecurityConfigEx02Test {

	private static MockMvc mvc;
	private static FilterChainProxy filterChainProxy;

	/**
	 * application contex 환경 세팅
	 */
	@BeforeAll
	public static void setup(WebApplicationContext webApplicationContext) {
		filterChainProxy = webApplicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
		mvc = MockMvcBuilders
				.webAppContextSetup(webApplicationContext)
				.addFilter(new DelegatingFilterProxy(filterChainProxy), "/*")
				.build();
	}
	
	@Test
	public void testSecurityFilterChains() {
		List<SecurityFilterChain> list = filterChainProxy.getFilterChains();
		assertEquals(2, list.size()); // "/assets/**", "/**"
	}
	
	@Test
	public void testSecurityFilterChain01() {
		// "/assets/**"의 filter chain에 등록된 filter의 개수 : 0
		SecurityFilterChain securityFilterChain = filterChainProxy.getFilterChains().getFirst();
		assertEquals(0, securityFilterChain.getFilters().size());
	}

	@Test
	public void testSecurityFilterChain02() {
		// "/**"의 filter chain에 등록된 filter의 개수 : 10(Default) : java 설정 기반
		SecurityFilterChain securityFilterChain = filterChainProxy.getFilterChains().getLast();
		List<Filter> filters = securityFilterChain.getFilters();
		
		assertEquals(10, filters.size());

		for (Filter filter : filters) {
			System.out.println(filter.getClass().getSimpleName());
		}
	}
	
	@Test
	public void testPing() throws Throwable {
		mvc
			.perform(get("/ping"))
			.andExpect(status().isOk())
			.andExpect(content().string("pong"))
			.andDo(print());
	}
	
	@Test
	public void testAssets() throws Throwable {
		mvc
			.perform(get("/assets/images/logo.svg"))
			.andExpect(status().isOk())
			.andExpect(content().contentType("image/svg+xml"))
			.andDo(print());
	}

}
