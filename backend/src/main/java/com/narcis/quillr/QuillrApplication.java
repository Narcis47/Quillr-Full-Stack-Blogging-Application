package com.narcis.quillr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class QuillrApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuillrApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

	@Bean
	public RestTemplate restTemplate() {return new RestTemplate();}
}
