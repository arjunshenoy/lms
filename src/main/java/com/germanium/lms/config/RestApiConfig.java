package com.germanium.lms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestApiConfig implements WebMvcConfigurer {

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
