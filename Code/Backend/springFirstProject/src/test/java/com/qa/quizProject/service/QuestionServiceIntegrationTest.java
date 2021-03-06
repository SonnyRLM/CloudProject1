package com.qa.quizProject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.persitance.repository.QuestionRepository;
import com.qa.quizProject.rest.dto.QuestionDTO;

@SpringBootTest
public class QuestionServiceIntegrationTest {

	@Autowired
	private QuestionService service;
	
	@Autowired
	private QuestionRepository repo;
	
	@Autowired 
	private ModelMapper mapper;
	
	private QuestionDTO mapToDTO(QuizQuestion desc) {
		return this.mapper.map(desc, QuestionDTO.class);
	}
	
	private QuizQuestion mapToPOJO(QuestionDTO questionDTO) {
		return this.mapper.map(questionDTO, QuizQuestion.class);
	}
	
	//QUIZ 1
	private final QuizQuestion Question1 = new QuizQuestion(1L, "Capital of England","London;Bristol;Manchester;Hull","London");		
	private final QuizQuestion Question2 = new QuizQuestion(2L, "Capital of New Zealand","Wellington;Christchurch;Dunedin;Auckland","Wellington");
	private final QuizQuestion Question3 = new QuizQuestion(3L, "Capital of Cuba","Camaguey;Guantanamo;Havana;Holguin","Havana");
	private final QuizQuestion Question4 = new QuizQuestion(4L, "Capital of Uruguay","Montevideo;Melo;Buenos Aires;Maldonado","Montevideo");
	
	
	private final List<QuizQuestion> QUESTIONS = List.of(Question1,Question2,Question3,Question4);
	
	
	@BeforeEach
	void setup() {
		this.repo.saveAll(QUESTIONS);
	}
	
	@Test
	void createTest() throws Exception {
		// Problem here is that service.create(q1) appends q1 to end of db and returns new entry
		assertThat(this.service.create(Question1)).isEqualTo(this.mapToDTO(Question1));
	}
	
	@Test
	void readByIdTest() throws Exception {
		assertThat(this.service.getQuestById(Question1.getQuestion_id())).isEqualTo(this.mapToDTO(Question1));

	}
	
	@Test
	void readAllTest() throws Exception {
		assertThat(this.service.getAllQuests().stream().map(this::mapToPOJO)).isEqualTo(QUESTIONS);
	}
	
	@Test
	void updateQuestionTest() throws Exception {
		assertThat(this.service.updateQuestion(Question1.getQuestion_id(), this.mapToDTO(Question1)))
		.isEqualTo(this.mapToDTO(Question1));
	}
	
	@Test
	void removeTest() throws Exception {
		assertThat(this.service.removeQuizQuest(Question2.getQuestion_id())).isTrue();
	}
	
}
