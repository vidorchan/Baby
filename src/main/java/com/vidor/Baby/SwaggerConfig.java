package com.vidor.Baby;

import com.vidor.Baby.interceptors.BabyInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
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

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.vidor.Baby.controller"))//any()
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
				.useDefaultResponseMessages(false)
				.directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
				.directModelSubstitute(java.time.OffsetDateTime.class, java.util.Date.class)
				.globalResponseMessage(RequestMethod.GET,
						newArrayList(new ResponseMessageBuilder()
										.code(500)
										.message("500 message")
										.responseModel(new ModelRef("Error"))
										.build(),
								new ResponseMessageBuilder()
										.code(403)
										.message("Forbidden!")
										.build()));

		//set URL for swagger tester page if configured
		//needed if behind a load balancer
//		String testURL = configService.getSwaggerTestURL();
//		if(testURL != null && !"".equals(testURL)){
//			docket.host(testURL);
//		}
	}
	/**
	 * Create object to hold information about the API.
	 * Note that the version property is automatically updated when the project is released.
	 *
	 * @return
	 */
	ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Vidor baby API")
				.description("Vidor Internet Booking Engine API")
				.license("")
//				.licenseUrl("http://www.openjawtech.com")
//				.termsOfServiceUrl("http://www.openjawtech.com")
				.version("0.38.0")
				.contact(new Contact("","", "1282911204@qq.com"))
				.build();
	}
}
