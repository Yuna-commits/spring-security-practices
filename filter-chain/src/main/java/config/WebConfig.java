package config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages={"controller"})
public class WebConfig implements WebMvcConfigurer {
	// 정적 자원 요청을 처리할 매핑 설정
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/assets/**")
                .addResourceLocations("classpath:/assets/"); // src/main/resources/assets/
    }
}