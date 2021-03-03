package com.qa.quizProject.service;

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
		
		
		
}
