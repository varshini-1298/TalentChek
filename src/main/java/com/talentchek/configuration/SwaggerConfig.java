package com.talentchek.configuration;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig {

	@Bean
	public Docket api() {
		//ParameterBuilder ObjParameterBuilder = new ParameterBuilder();
		//ObjParameterBuilder.name("Authorization").parameterType("header").required(true).build();
		//List<Parameter> aParameters = new ArrayList<Parameter>();
		//aParameters.add(ObjParameterBuilder.build());

		return new Docket(DocumentationType.SWAGGER_2).securityContexts(Arrays.asList(securityContext())).securitySchemes(Arrays.asList(apiKey())).select().apis(RequestHandlerSelectors.any())
				// .apis(Predicates.not(RequestHandlerSelectors.basePackage("com.paragon.visitorchek")))
				.paths(PathSelectors.any())
				// .paths(PathSelectors.ant("/swagger2"))
				.build()
				.apiInfo(apiInfo());
		// .globalOperationParameters(aParameters);

	}

	private ApiKey apiKey() {
		return new ApiKey("Access Token", "Authorization", "header");
	}

	private SecurityContext securityContext() { 
	    return SecurityContext.builder().securityReferences(defaultAuth()).build(); 
	} 

	private List<SecurityReference> defaultAuth() { 
	    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything"); 
	    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1]; 
	    authorizationScopes[0] = authorizationScope; 
	    return Arrays.asList(new SecurityReference("Access Token", authorizationScopes)); 
	}
	
	@Bean
	public WebMvcConfigurer webMvcConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
				registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
				registry.addResourceHandler("/webjars/**")
						.addResourceLocations("classpath:/META-INF/resources/webjars/");
			}
		};
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "TalentChek", 
	      "TalentChek API spec documentation", 
	      "1.0", 
	      "Terms of service", 
	      new Contact("www.paragondynamics.in", "Paragon Dynamics Info Systems Pvt Ltd",""), 
	      "License of API", "API license URL", Collections.emptyList());
	}

}