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
import com.qa.quizProject.persitance.repository.QuizRepository;
import com.qa.quizProject.rest.dto.DescriptionDTO;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DescriptionServiceUnitTest {

		@Autowired 
		private DescriptionService service;
		
		@MockBean
		private QuizRepository repo;
		
		@Autowired
		private ModelMapper mapper;
		
		private DescriptionDTO mapToDTO(QuizDescription desc) {
			return this.mapper.map(desc, DescriptionDTO.class);
		}
		
		private final QuizDescription Quiz1 = new QuizDescription(1L, "Capital Cities", "Select the Capital city of these counties");		
		private final QuizDescription Quiz2 = new QuizDescription(2L, "Continents", "Select the continent that these countries are in");
		private final QuizDescription Quiz3 = new QuizDescription(3L, "General Knowledge", "Test your brain with this variety quiz");
	
		private final List<QuizDescription> QUIZZES = List.of(Quiz1,Quiz2,Quiz3);
		
		@Test
		void createTest() throws Exception{
			when(this.repo.save(Quiz1)).thenReturn(Quiz1);
			assertThat(this.service.create(Quiz1)).isEqualTo(this.mapToDTO(Quiz1));
			verify(this.repo, atLeastOnce()).save(Quiz1);
		}
		
		@Test
		void readByIdTest() throws Exception {
			when(this.repo.findById(Quiz1.getQuiz_id())).thenReturn(Optional.of(Quiz1));
			assertThat(this.service.getDescById(Quiz1.getQuiz_id())).isEqualTo(this.mapToDTO(Quiz1));
			verify(this.repo, atLeastOnce()).findById(Quiz1.getQuiz_id());	
		}
		
		@Test
		void readAllTest() throws Exception{
			when(this.repo.findAll()).thenReturn(QUIZZES);
			assertThat(this.service.getAllDesc().isEmpty()).isFalse();
			verify(this.repo, atLeastOnce()).findAll();
		}
		
		@Test
		void updateQuestionTest() throws Exception{
			when(this.repo.findById(Quiz1.getQuiz_id())).thenReturn(Optional.of(Quiz1));
			when(this.repo.save(Quiz1)).thenReturn(Quiz1);
			assertThat(this.service.updateQuizDesc(Quiz1.getQuiz_id(), this.mapToDTO(Quiz1)))
							.isEqualTo(this.mapToDTO(Quiz1));
			verify(this.repo, atLeastOnce()).findById(Quiz1.getQuiz_id());
			verify(this.repo, atLeastOnce()).save(Quiz1);
		}
		
		@Test
		void removeTest() throws Exception {
			boolean found = false;
			when(this.repo.existsById(Quiz1.getQuiz_id())).thenReturn(found);
			assertThat(this.service.removeQuizDesc(Quiz1.getQuiz_id())).isNotEqualTo(found);
			verify(this.repo, atLeastOnce()).existsById(Quiz1.getQuiz_id());
		}
		
}
