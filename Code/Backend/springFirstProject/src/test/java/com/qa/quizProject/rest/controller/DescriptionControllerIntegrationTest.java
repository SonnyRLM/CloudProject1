package com.qa.quizProject.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.rest.dto.DescriptionDTO;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class DescriptionControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private ModelMapper mapper;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	private DescriptionDTO mapToDTO(QuizDescription desc) {
		return this.mapper.map(desc, DescriptionDTO.class);
	}
	
	private final QuizQuestion Question1 = new QuizQuestion(1L, "Capital of England","London;Bristol;Manchester;Hull","London");		
	private final QuizQuestion Question2 = new QuizQuestion(2L, "Capital of New Zealand","Wellington;Christchurch;Dunedin;Auckland","Wellington");
	private final QuizQuestion Question3 = new QuizQuestion(3L, "Capital of Cuba","Camaguey;Guantanamo;Havana;Holguin","Havana");
	private final QuizQuestion Question4 = new QuizQuestion(4L, "Capital of Uruguay","Montevideo;Melo;Buenos Aires;Maldonado","Montevideo");
	private final List<QuizQuestion> QUESTIONS = List.of(Question1,Question2,Question3,Question4);
	
	private final QuizDescription Quiz1 = new QuizDescription(1L, "Capital Cities", "Select the Capital city of these counties", QUESTIONS);		
	private final QuizDescription Quiz2 = new QuizDescription(2L, "Continents", "Select the continent that these countries are in");
	private final QuizDescription Quiz3 = new QuizDescription(3L, "General Knowledge", "Test your brain with this variety quiz");
	private final List<QuizDescription> QUIZZES = List.of(Quiz1,Quiz2,Quiz3);
	
	
	@Test
	void createTest() throws Exception {
		this.mvc.perform(post("/quiz/create").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(this.jsonifier.writeValueAsString(Quiz1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(Quiz1))));
	}
	
	@Test
	void getAllTest() throws Exception {
		 Quiz1.setQuestions(QUESTIONS);
	        this.mvc.perform(get("/quiz/getAll").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
	                .andExpect(content()
	                .json(this.jsonifier.writeValueAsString(QUIZZES.stream().map(this::mapToDTO).collect(Collectors.toList()))));
	}
	
	@Test
	void getById() throws Exception {
		 Quiz1.setQuestions(QUESTIONS);
	        this.mvc.perform(get("/quiz/getById/" + Quiz1.getQuiz_id()).accept(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk())
	                .andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(Quiz1))));
	}
	
	
	
	
}


















