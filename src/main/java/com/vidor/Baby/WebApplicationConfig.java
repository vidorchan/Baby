package com.vidor.Baby;

import com.vidor.Baby.interceptors.BabyInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
public class WebApplicationConfig extends WebMvcConfigurationSupport{

	//List of paths to ignore for interceptors
	private static final String[] EXCLUDES = {"/swagger-ui.html", "/", "/webjars/**", "/swagger-resources/**",
			"/v2/api-docs", "/management" };

	protected BabyInterceptor babyInterceptor;

	public WebApplicationConfig(BabyInterceptor babyInterceptor) {
		this.babyInterceptor = babyInterceptor;
	}

	@Override
	protected void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(babyInterceptor).addPathPatterns("/**")
				.excludePathPatterns(EXCLUDES);
		super.addInterceptors(registry);
	}

	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
