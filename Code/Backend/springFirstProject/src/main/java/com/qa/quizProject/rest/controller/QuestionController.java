package com.qa.quizProject.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.rest.dto.QuestionDTO;
import com.qa.quizProject.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionController {
	
	private QuestionService service;
	
	@Autowired
	public QuestionController(QuestionService service) {
		super();
		this.service=service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<QuestionDTO> create(@RequestBody QuizQuestion quest){
		return new ResponseEntity<>(this.service.create(quest), HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<QuestionDTO>> getAllQuest(){
		return ResponseEntity.ok(this.service.getAllQuests());
	}
	
	@GetMapping("/getByID/{id}")
	public ResponseEntity<QuestionDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.getQuestById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<QuestionDTO> updateQuest(@PathVariable Long id, @RequestBody QuestionDTO question){
		return new ResponseEntity<>(this.service.updateQuestion(id, question), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<QuestionDTO> deleteQuest(@PathVariable Long id){
		if(this.service.removeQuizQuest(id)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
	
	
	
}
	

