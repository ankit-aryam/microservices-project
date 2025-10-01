package com.assignment.order_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableKafka
public class OrderServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrderServiceApplication.class, args);
	}

	@Configuration
	public class SecurityConfig {

		@Bean
		public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
			http
					.csrf(csrf -> csrf.disable())
					.authorizeHttpRequests(auth -> auth
							.anyRequest().permitAll()
					);

			return http.build();
		}
	}

}


