package com.trustly.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket testTrustly() {
		
		return  new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.trustly"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(projectInfo());
	}
	
	private ApiInfo projectInfo() {
	 ApiInfoBuilder builder = new ApiInfoBuilder();
	 builder.title("GitHub Api Crawler");
	 builder.description(" Test for Java Senior Backed @ Trustly");
	 builder.version("1.0");
	 builder.termsOfServiceUrl("http://google.com.br");
	 builder.contact(new Contact("Rafael Moura", "https://www.linkedin.com/in/rafsmoura/", "rafael.mgbr[@]gmail.com"));
	 builder.license("Licensed under the Apache License, Version 2.0 (the \"License\")");
	 builder.licenseUrl("http://www.apache.org/licenses/LICENSE-2.0");
	 return builder.build();
	}
	

}
