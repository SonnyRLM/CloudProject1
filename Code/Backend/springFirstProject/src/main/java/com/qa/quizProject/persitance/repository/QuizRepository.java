package com.qa.quizProject.persitance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.quizProject.persitance.domain.QuizDescription;

public interface QuizRepository extends JpaRepository<QuizDescription, Long> {
	
	//Run SELECT * FROM quiz_description WHERE quiz_name = 'something';
	QuizDescription findQuizDescriptionByQuizName(String quizName);
	
	
}

