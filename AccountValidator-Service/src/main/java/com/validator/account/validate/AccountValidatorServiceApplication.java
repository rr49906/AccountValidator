package com.validator.account.validate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class AccountValidatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountValidatorServiceApplication.class, args);
	}

	/**
	 * Rest Template used to invoke rest APIs
	 * @return RestTemplate Object.
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
