package com.qa.quizProject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.persitance.repository.QuizRepository;
import com.qa.quizProject.rest.dto.DescriptionDTO;

@SpringBootTest
public class DescriptionServiceIntegrationTest {
	
	@Autowired
	private DescriptionService service;
	
	@Autowired
	private QuizRepository repo;
	
	@Autowired 
	private ModelMapper mapper;
	
	private DescriptionDTO mapToDTO(QuizDescription desc) {
		return this.mapper.map(desc, DescriptionDTO.class);
	}
	
	private QuizDescription mapToPOJO(DescriptionDTO questionDTO) {
		return this.mapper.map(questionDTO, QuizDescription.class);
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
	
	@BeforeEach
	void setup() {
		this.repo.saveAll(QUIZZES);
	}
	
	@Test
	void createTest() throws Exception {
		// Problem here is that service.create(q1) appends q1 to end of db and returns new entry
		assertThat(this.service.create(Quiz1)).isEqualTo(this.mapToDTO(Quiz1));
	}
	
	@Test
	void readByIdTest() throws Exception {
		assertThat(this.service.getDescById(Quiz1.getQuiz_id())).isEqualTo(this.mapToDTO(Quiz1));

	}
	
	@Test
	void readAllTest() throws Exception {
		assertThat(this.service.getAllDesc().stream().map(this::mapToPOJO)).isEqualTo(QUIZZES);
	}
	
	@Test
	void updateQuestionTest() throws Exception {
		assertThat(this.service.updateQuizDesc(Quiz1.getQuiz_id(), this.mapToDTO(Quiz1)))
		.isEqualTo(this.mapToDTO(Quiz1));
	}
	
	@Test
	void removeTest() throws Exception {
		assertThat(this.service.removeQuizDesc(Quiz2.getQuiz_id())).isTrue();
	}
	
	
}
