package com.qa.quizProject.persitance.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizQuestion {

	@Id //Primary
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment
	private Long question_id;
	
	@Column
	private String question;
	
	@Column
	private String answers;
	
	@Column 
	private String correct;

	@ManyToOne
	private QuizDescription quizDescription;
	
	
}
