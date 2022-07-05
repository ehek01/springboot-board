package com.nhnacademy.myhome;

import com.nhnacademy.myhome.entity.Role;
import com.nhnacademy.myhome.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class MyHomeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyHomeApplication.class, args);
	}


	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.createRole("ROLE_TEST");
		};
	}
}
