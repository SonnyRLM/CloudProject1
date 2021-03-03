package com.qa.quizProject.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.rest.dto.DescriptionDTO;
import com.qa.quizProject.service.DescriptionService;

@CrossOrigin
@RestController
@RequestMapping("/quiz")
public class DescriptionController {

	private DescriptionService service;
	
	@Autowired
	public DescriptionController(DescriptionService service) {
		super();
		this.service = service;
	}
	
	@PostMapping("/create")
	public ResponseEntity<DescriptionDTO> create(@RequestBody QuizDescription desc){
		DescriptionDTO created = this.service.create(desc);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<DescriptionDTO>> getAllDesc(){
		return ResponseEntity.ok(this.service.getAllDesc());
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<DescriptionDTO> getById(@PathVariable Long id){
		return ResponseEntity.ok(this.service.getDescById(id));
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<DescriptionDTO> updateDesc(@PathVariable Long id, @RequestBody DescriptionDTO descriptionDTO){
		return new ResponseEntity<>(this.service.updateQuizDesc(id, descriptionDTO), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<DescriptionDTO> deleteDesc(@PathVariable Long id){
		if(this.service.removeQuizDesc(id)) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
	}
}









