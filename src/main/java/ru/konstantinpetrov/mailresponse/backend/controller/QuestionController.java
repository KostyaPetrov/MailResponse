package ru.konstantinpetrov.mailresponse.backend.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;

@RestController
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping(path="/getQuestion")
	public ResponseEntity<ResponseQuestionDTO> getQuestion() {
		try {
			List<Question> responseList = questionService.getQuestion();
			ResponseQuestionDTO response=new ResponseQuestionDTO(responseList, "Succes getted");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            ResponseQuestionDTO response=new ResponseQuestionDTO(null, "Error receiving questions");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @GetMapping(path="/getSortQuestion")
	public ResponseEntity<ResponseQuestionDTO> getSortQuestion() {
		try {
			List<Question> responseList = questionService.getQuestion();
            responseList.sort(Comparator.comparing(Question::getCountReview).reversed());
            
			ResponseQuestionDTO response=new ResponseQuestionDTO(responseList, "Succes getted");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            ResponseQuestionDTO response=new ResponseQuestionDTO(null, "Error receiving questions");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}
}
