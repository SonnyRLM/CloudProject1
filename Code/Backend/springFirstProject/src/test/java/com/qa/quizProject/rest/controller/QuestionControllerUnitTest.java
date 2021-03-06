package com.qa.quizProject.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.rest.dto.QuestionDTO;
import com.qa.quizProject.service.QuestionService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class QuestionControllerUnitTest {
	
	@Autowired
	private QuestionController controller;
	 
	@MockBean
	private QuestionService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private QuestionDTO mapToDTO(QuizQuestion question) {
        return this.mapper.map(question, QuestionDTO.class);
    }
	
	private final QuizQuestion Question1 = new QuizQuestion(1L, "Capital of England", "London;Bristol;Nottingham;Bath","London");		
	private final QuizQuestion Question2 = new QuizQuestion(2L, "Capital of France", "Bordaux;Paris;Nice;Bruges", "Paris");
	private final QuizQuestion Question3 = new QuizQuestion(3L, "Capital of New Zealand", "Christchurch;Cityname;Hobbitland;Wellington", "Wellington");
	private final QuizQuestion Question4 = new QuizQuestion(4L, "Capital of Kosovo", "Prizren;Peja;Pristina;Viti", "Pristina");
	
	private final List<QuizQuestion> QUESTIONS = List.of(Question1,Question2,Question3,Question4);
	
	
	@Test
	void createTest() throws Exception {
        when(this.service.create(Question1)).thenReturn(this.mapToDTO(Question1));
        assertThat(new ResponseEntity<QuestionDTO>(this.mapToDTO(Question1), HttpStatus.CREATED))
                .isEqualTo(this.controller.create(Question1));
        verify(this.service, atLeastOnce()).create(Question1);
	}
	
	@Test
	void readByIdTest() throws Exception {
        when(this.service.getQuestById(Question1.getQuestion_id())).thenReturn(this.mapToDTO(Question1));
        assertThat(new ResponseEntity<QuestionDTO>(this.mapToDTO(Question1), HttpStatus.OK))
                .isEqualTo(this.controller.getById(Question1.getQuestion_id()));
        verify(this.service, atLeastOnce()).getQuestById(Question1.getQuestion_id());
	}
 	
	@Test
	void realAllQuestsTest() throws Exception {
        when(this.service.getAllQuests()).thenReturn(QUESTIONS.stream().map(this::mapToDTO).collect(Collectors.toList()));
        assertThat(this.controller.getAllQuest().getBody().isEmpty()).isFalse();
        verify(this.service, atLeastOnce()).getAllQuests();
	}
	
	@Test
	void updateQuestTest() throws Exception {
		when(this.service.updateQuestion(Question2.getQuestion_id(), this.mapToDTO(Question2)))
        	.thenReturn(this.mapToDTO(Question2));
		assertThat(new ResponseEntity<QuestionDTO>(this.mapToDTO(Question2), HttpStatus.ACCEPTED))
        	.isEqualTo(this.controller.updateQuest(Question2.getQuestion_id(), this.mapToDTO(Question2)));
		verify(this.service, atLeastOnce()).updateQuestion(Question2.getQuestion_id(), this.mapToDTO(Question2));
	}
	
	@Test
	void deleteTest() throws Exception {
		this.controller.deleteQuest(Question3.getQuestion_id());
		verify(this.service, atLeastOnce()).removeQuizQuest(Question3.getQuestion_id());
	}
	
}













