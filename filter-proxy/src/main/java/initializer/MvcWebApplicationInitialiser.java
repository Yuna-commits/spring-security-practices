package initializer;

import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import config.AppConfig;
import config.WebConfig;
import jakarta.servlet.Filter;

/**
 * web.xml 설정 기반
 */
public class MvcWebApplicationInitialiser extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] { AppConfig.class };
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] { WebConfig.class };
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		// root context의 filter bean을 사용하기 위해 bean의 메소드명으로 타겟팅
		return new Filter[] {new DelegatingFilterProxy("realFilter")};
	}
	
}
