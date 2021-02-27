package com.qa.quizProject.rest.dto;

import java.util.ArrayList;
import java.util.List;

import com.qa.quizProject.persitance.domain.QuizQuestion;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DescriptionDTO {

	// data transfer object
	// represents the structure of out data in a nice readable format 
	
	private long quizId;
	private String quizDescription;
	private String quizName;
	private List<QuestionDTO> questions = new ArrayList<>();

	
		
}
