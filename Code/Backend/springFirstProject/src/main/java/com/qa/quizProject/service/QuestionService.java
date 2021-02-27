package com.qa.quizProject.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qa.quizProject.persitance.domain.QuizDescription;
import com.qa.quizProject.persitance.domain.QuizQuestion;
import com.qa.quizProject.persitance.repository.QuestionRepository;
import com.qa.quizProject.rest.dto.DescriptionDTO;
import com.qa.quizProject.rest.dto.QuestionDTO;
import com.qa.quizProject.utils.ProjectUtils;

@Service
public class QuestionService {

	private QuestionRepository repo;
	private ModelMapper mapper;
	
	@Autowired
	public QuestionService(QuestionRepository repo, ModelMapper mapper){
		super();
		this.repo = repo;
		this.mapper = mapper;
	}
	
	private QuestionDTO mapToDTO(QuizQuestion quest) {
		return this.mapper.map(quest, QuestionDTO.class);
	}
	
	public QuestionDTO create(QuizQuestion quest) {
		return this.mapToDTO(this.repo.save(quest));
	}
	
	public List<QuestionDTO> getAllQuests(){
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
//		List<QuizQuestion> allQuest = this.repo.findAll();
//		return allQuest;
	}
	
	public QuestionDTO getQuestById(Long id) {
		return this.mapToDTO(this.repo.findById(id).orElseThrow());
		//return this.repo.findById(id).get();
	}
	
	public QuestionDTO updateQuestion(Long id, QuestionDTO quest) {
		QuizQuestion questChange = this.repo.findById(id).orElseThrow();
	
		questChange.setQuestion(quest.getQuestion());
		questChange.setAnswers(quest.getAnswers());
		questChange.setCorrect(quest.getCorrect());
		
		ProjectUtils.mergeNotNull(quest, questChange);
		return this.mapToDTO(questChange);
	}
	
	public boolean removeQuizQuest(Long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}
}





