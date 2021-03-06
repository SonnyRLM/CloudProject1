package com.qa.quizProject.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.persitance.repository.QuestionRepository;
import com.qa.quizProject.rest.dto.QuestionDTO;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class QuestionServiceUnitTest {

		@Autowired 
		private QuestionService service;
		
		@MockBean
		private QuestionRepository repo;
		
		@Autowired
		private ModelMapper mapper;
		
		private QuestionDTO mapToDTO(QuizQuestion desc) {
			return this.mapper.map(desc, QuestionDTO.class);
		}
		
		private final QuizQuestion Question1 = new QuizQuestion(1L, "Capital of England", "London;Bristol;Nottingham;Bath","London");		
		private final QuizQuestion Question2 = new QuizQuestion(2L, "Capital of France", "Bordaux;Paris;Nice;Bruges", "Paris");
		private final QuizQuestion Question3 = new QuizQuestion(3L, "Capital of New Zealand", "Christchurch;Cityname;Hobbitland;Wellington", "Wellington");
		private final QuizQuestion Question4 = new QuizQuestion(4L, "Capital of Kosovo", "Prizren;Peja;Pristina;Viti", "Pristina");
		
		private final List<QuizQuestion> QUESTIONS = List.of(Question1,Question2,Question3,Question4);
		
		
		@Test
		void createTest() throws Exception{
			when(this.repo.save(Question1)).thenReturn(Question1);
			assertThat(this.service.create(Question1)).isEqualTo(this.mapToDTO(Question1));
			verify(this.repo, atLeastOnce()).save(Question1);
		}
		
		@Test
		void readByIdTest() throws Exception {
			when(this.repo.findById(Question1.getQuestion_id())).thenReturn(Optional.of(Question1));
			assertThat(this.service.getQuestById(Question1.getQuestion_id())).isEqualTo(this.mapToDTO(Question1));
			verify(this.repo, atLeastOnce()).findById(Question1.getQuestion_id());	
		}
		
		@Test
		void readAllTest() throws Exception{
			when(this.repo.findAll()).thenReturn(QUESTIONS);
			assertThat(this.service.getAllQuests().isEmpty()).isFalse();
			verify(this.repo, atLeastOnce()).findAll();
		}
		
		@Test
		void updateQuestionTest() throws Exception{
			when(this.repo.findById(Question1.getQuestion_id())).thenReturn(Optional.of(Question1));
			when(this.repo.save(Question1)).thenReturn(Question1);
			assertThat(this.service.updateQuestion(Question1.getQuestion_id(), this.mapToDTO(Question1)))
							.isEqualTo(this.mapToDTO(Question1));
			verify(this.repo, atLeastOnce()).findById(Question1.getQuestion_id());
			verify(this.repo, atLeastOnce()).save(Question1);
		}
		
		@Test
		void removeTest() throws Exception {
			boolean found = false;
			when(this.repo.existsById(Question1.getQuestion_id())).thenReturn(found);
			assertThat(this.service.removeQuizQuest(Question1.getQuestion_id())).isNotEqualTo(found);
			verify(this.repo, atLeastOnce()).existsById(Question1.getQuestion_id());
		}
}
