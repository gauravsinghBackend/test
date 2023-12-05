package com.example.LoginPage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@EnableDiscoveryClient
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ManagementWebSecurityAutoConfiguration.class})
public class LoginPageApplication {

	public static void main(String[] args) {

		SpringApplication.run(LoginPageApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer()
	{
		return new WebMvcConfigurer(){
			public void addCorsMapping(CorsRegistry registry){
				registry.addMapping("/**").allowedOrigins("*");
			}
		};
	}
}
