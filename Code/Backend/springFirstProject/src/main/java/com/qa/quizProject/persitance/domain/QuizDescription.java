package com.qa.quizProject.persitance.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizDescription {

	@Id //primary key
	@GeneratedValue(strategy= GenerationType.IDENTITY) // auto increment
	private Long quiz_id;
	
	@Column
	private String quizDescription;
	
	@Column
	private String quizName;
	
	@OneToMany(mappedBy = "quizDescription", fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<QuizQuestion> questions = new ArrayList<>();

	
	
	
}
