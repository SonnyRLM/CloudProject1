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
import com.qa.quizProject.rest.dto.QuestionDTO;


@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"classpath:data.sql"}, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@ActiveProfiles(profiles = "test")
public class QuestionControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private ModelMapper mapper;
	
	@Autowired
	private ObjectMapper jsonifier;
	
	private QuestionDTO mapToDTO(QuizQuestion desc) {
		return this.mapper.map(desc, QuestionDTO.class);
	}
	
	private final QuizQuestion Question1 = new QuizQuestion(1L, "Capital of England","London;Bristol;Manchester;Hull","London");		
	private final QuizQuestion Question2 = new QuizQuestion(2L, "Capital of New Zealand","Wellington;Christchurch;Dunedin;Auckland","Wellington");
	private final QuizQuestion Question3 = new QuizQuestion(3L, "Capital of Cuba","Camaguey;Guantanamo;Havana;Holguin","Havana");
	private final QuizQuestion Question4 = new QuizQuestion(4L, "Capital of Uruguay","Montevideo;Melo;Buenos Aires;Maldonado","Montevideo");
	private final List<QuizQuestion> QUESTIONS = List.of(Question1,Question2,Question3,Question4);
	
	
	@Test
	void createTest() throws Exception {
		this.mvc.perform(post("/question/create").accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(this.jsonifier.writeValueAsString(Question1)))
                .andExpect(status().isCreated())
                .andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(Question1))));
	}
	
	
	@Test
	void getByIDTest() throws Exception {
	       this.mvc.perform(get("/question/getByID/" + Question1.getQuestion_id()).accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(Question1))));
	}
	
	
	@Test
	void getAllTest() throws Exception {
		this.mvc.perform(get("/question/getAll").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
        .andExpect(content().json(this.jsonifier
                .writeValueAsString(QUESTIONS.stream().map(this::mapToDTO).collect(Collectors.toList()))));
	}
	
	
	@Test
	void updateQuestTest() throws Exception {
		this.mvc.perform(put("/question/update/" + Question1.getQuestion_id()).accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON).content(this.jsonifier.writeValueAsString(Question1)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(this.jsonifier.writeValueAsString(this.mapToDTO(Question1))));
	}
	
	
	@Test
	void deleteQuestTest() throws Exception {
		this.mvc.perform(delete("/question/delete/" + Question3.getQuestion_id())).andExpect(status().isNoContent());
	}
	
	
	
}


























