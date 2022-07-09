package com.nhnacademy.myhome;

import com.nhnacademy.myhome.entity.Board;
import com.nhnacademy.myhome.entity.Role;
import com.nhnacademy.myhome.entity.User;
import com.nhnacademy.myhome.repository.BoardRepository;
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
	CommandLineRunner run(UserService userService, BoardRepository boardRepository) {
		return args -> {
			// role init
			userService.createRole("ROLE_USER");
			userService.createRole("ROLE_ADMIN");

			// user init
			User user = new User(null, "user", "1", false, new ArrayList<>(), new ArrayList<>());
			User gildong = new User(null, "gildong", "1", false, new ArrayList<>(), new ArrayList<>());
			userService.createUser(user);
			userService.createUser(gildong);

			// board init
			boardRepository.save(new Board(null, "초기제목1", "초기내용1", user));
		};
	}
}
