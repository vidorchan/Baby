package com.vidor.Baby;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.newArrayList;

/**
 * ReloadableResourceBundleMessageSource vs ResourceBundleMessageSource:
 * 1) It is not restricted to read .properties files alone but can read xml property files as well.
 2) It is not restricted to reading files from just classpath but from any location.
 */

@Configuration
public class ResourceBundleConfig {

	/**
	 * Accesses error messages with basename 'errors'
	 * @return MessageSource
	 */
	@Bean(value = "messageSource")
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:i18n/errors");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(false);
		return messageSource;
	}

	/**
	 * Accesses error messages with basename 'deverrors'
	 * @return MessageSource
	 */
	@Bean(value = "devErrorMessageSource")
	public MessageSource devErrorMessageSource() {
		ReloadableResourceBundleMessageSource devErrorMessageSource = new ReloadableResourceBundleMessageSource();
		devErrorMessageSource.setBasenames("classpath:i18n/deverrors");
		devErrorMessageSource.setDefaultEncoding("UTF-8");
		devErrorMessageSource.setUseCodeAsDefaultMessage(false);
		return devErrorMessageSource;
	}
}
