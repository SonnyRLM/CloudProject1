package com.qa.quizProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class quizProjectApplication {

		public static void main(String[] args) {
			SpringApplication.run(quizProjectApplication.class, args);
		}
}
