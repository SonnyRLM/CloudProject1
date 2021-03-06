package com.qa.quizProject.rest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.rest.dto.DescriptionDTO;
import com.qa.quizProject.rest.dto.QuestionDTO;
import com.qa.quizProject.service.DescriptionService;
import com.qa.quizProject.service.QuestionService;

@SpringBootTest
@ActiveProfiles(profiles = "test")
public class DescriptionControllerUnitTest {

	@Autowired
	private DescriptionController controller;
	 
	@MockBean
	private DescriptionService service;
	
	@Autowired
	private ModelMapper mapper;
	
	private DescriptionDTO mapToDTO(QuizDescription question) {
        return this.mapper.map(question, DescriptionDTO.class);
    }
	
	private final QuizDescription Quiz1 = new QuizDescription(1L, "Capital Cities", "Select the Capital city of these counties");		
	private final QuizDescription Quiz2 = new QuizDescription(2L, "Continents", "Select the continent that these countries are in");
	private final QuizDescription Quiz3 = new QuizDescription(3L, "General Knowledge", "Test your brain with this variety quiz");

	private final List<QuizDescription> QUIZZES = List.of(Quiz1,Quiz2,Quiz3);
	
	
	@Test
	void createTest() throws Exception{
        when(this.service.create(Quiz1)).thenReturn(this.mapToDTO(Quiz1));
        assertThat(this.controller.create(Quiz1))
                .isEqualTo(new ResponseEntity<DescriptionDTO>(this.mapToDTO(Quiz1), HttpStatus.CREATED));
        verify(this.service, atLeastOnce()).create(Quiz1);
	}
	
	@Test
	void readByIdTest() throws Exception {
		when(this.service.getDescById(Quiz1.getQuiz_id())).thenReturn(this.mapToDTO(Quiz1));
        assertThat(this.controller.getById(Quiz1.getQuiz_id()))
                .isEqualTo(new ResponseEntity<DescriptionDTO>(this.mapToDTO(Quiz1), HttpStatus.OK));
        verify(this.service, atLeastOnce()).getDescById(Quiz1.getQuiz_id());
	}
	
	@Test
	void getAllTest() throws Exception {
		when(this.service.getAllDesc()).thenReturn(QUIZZES.stream().map(this::mapToDTO).collect(Collectors.toList()));
        assertThat(this.controller.getAllDesc().getBody().isEmpty()).isFalse();
        verify(this.service, atLeastOnce()).getAllDesc();
	}
 	
	@Test
	void updateDescTest() throws Exception {
        when(this.service.updateQuizDesc(Quiz1.getQuiz_id(), this.mapToDTO(Quiz1))).thenReturn(this.mapToDTO(Quiz1));
        assertThat(this.controller.updateDesc(Quiz1.getQuiz_id(), this.mapToDTO(Quiz1)))
                .isEqualTo(new ResponseEntity<DescriptionDTO>(this.mapToDTO(Quiz1), HttpStatus.ACCEPTED));
        verify(this.service, atLeastOnce()).updateQuizDesc(Quiz1.getQuiz_id(), this.mapToDTO(Quiz1));
	}
	
    @Test
    void deleteDescTest() throws Exception {
        this.controller.deleteDesc(Quiz3.getQuiz_id());
        verify(this.service, atLeastOnce()).removeQuizDesc(Quiz3.getQuiz_id());
    }
	
	
	
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


