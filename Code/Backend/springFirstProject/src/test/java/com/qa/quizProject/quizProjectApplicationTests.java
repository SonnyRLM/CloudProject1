package com.qa.quizProject;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class quizProjectApplicationTests {
	
	@SuppressWarnings("static-access")
	@Test
	void contextLoads() {
		quizProjectApplication app = new quizProjectApplication();
		String[] args = {};
		app.main(args);
		assertThat(app).isInstanceOf(quizProjectApplication.class);
	}
}
