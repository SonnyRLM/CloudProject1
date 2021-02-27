package com.qa.quizProject.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class QuestionDTO {

	private Long question_id;
	private String question;
	private String answers;
	private String correct;
}
