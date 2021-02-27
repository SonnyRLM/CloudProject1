package com.qa.quizProject.persitance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qa.quizProject.persitance.domain.QuizQuestion;


public interface QuestionRepository extends JpaRepository<QuizQuestion, Long> {
	
}
