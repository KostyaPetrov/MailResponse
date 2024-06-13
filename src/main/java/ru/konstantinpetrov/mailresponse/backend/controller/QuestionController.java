package ru.konstantinpetrov.mailresponse.backend.controller;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class QuestionController {
    private QuestionService questionService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }


    @GetMapping(path="/getQuestion")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<ResponseQuestionDTO> getQuestion() {
		try {
			List<Question> responseList = questionService.getQuestion();
			ResponseQuestionDTO response=new ResponseQuestionDTO(responseList, "Success get");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseQuestionDTO response=new ResponseQuestionDTO(null, "Error receiving questions");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @PostMapping(path="/addQuestion")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<ResponseEnterDTO> addQuestion(@RequestBody Question question) {
		try {
            System.out.println(question);
			questionService.addQuestion(question);
            
			ResponseEnterDTO response=new ResponseEnterDTO(true, "Успешно");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseEnterDTO response=new ResponseEnterDTO(false, "Произошла ошибка");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @GetMapping(path="/getSortQuestion")
    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
	public ResponseEntity<ResponseQuestionDTO> getSortQuestion() {
		try {
			List<Question> responseList = questionService.getQuestion();
            responseList.sort(Comparator.comparing(Question::getCountReview).reversed());
            
			ResponseQuestionDTO response=new ResponseQuestionDTO(responseList, "Succes getted");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseQuestionDTO response=new ResponseQuestionDTO(null, "Error receiving questions");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @CrossOrigin
    @DeleteMapping(path="/deleteQuestionById/{userId}/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ResponseEnterDTO> deleteQuestionById(@PathVariable Long userId, @PathVariable Long id) {
		try {
            System.out.println(userId + " " + id);
            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                ResponseEnterDTO response=new ResponseEnterDTO(false, "Пользователь модератор");
                return new ResponseEntity<>(response,
                        HttpStatus.BAD_REQUEST);
            }
			questionService.deleteQuestionById(id);
            ResponseEnterDTO response=new ResponseEnterDTO(true, "Успешно!");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseEnterDTO response=new ResponseEnterDTO(false, "Произошла ошибка");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @CrossOrigin
    @DeleteMapping(path="/deleteQuestionByUserId/{userId}/{commentatorId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ResponseEnterDTO> deleteQuestionByUserId(@PathVariable Long userId, @PathVariable Long commentatorId) {
		try {
            System.out.println(userId + " " + commentatorId);
            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                ResponseEnterDTO response=new ResponseEnterDTO(false, "Пользователь модератор");
                return new ResponseEntity<>(response,
                        HttpStatus.BAD_REQUEST);
            }
			questionService.deleteQuestionByUserId(commentatorId);
            ResponseEnterDTO response=new ResponseEnterDTO(true, "Успешно!");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseEnterDTO response=new ResponseEnterDTO(false, "Произошла ошибка");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}


    @CrossOrigin
    @DeleteMapping(path="/deleteQuestionByPermissionStatus/{userId}/{permissionStatus}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ResponseEnterDTO> deleteQuestionByPermissionStatus(@PathVariable Long userId, @PathVariable PermissionStatus permissionStatus) {
		try {
            System.out.println(userId + " " + permissionStatus);
            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                ResponseEnterDTO response=new ResponseEnterDTO(false, "Пользователь модератор");
                return new ResponseEntity<>(response,
                        HttpStatus.BAD_REQUEST);
            }
			questionService.deleteQuestionByPermissionStatus(permissionStatus);
            ResponseEnterDTO response=new ResponseEnterDTO(true, "Успешно!");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseEnterDTO response=new ResponseEnterDTO(false, "Произошла ошибка");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @PostMapping("/changePermissionStatus/{userId}/{questionId}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<ResponseEnterDTO> changePermissionStatus(@PathVariable Long userId, @PathVariable Long questionId) {
        try {
            System.out.println(userId + " " + questionId);
            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                ResponseEnterDTO response=new ResponseEnterDTO(false, "Пользователь модератор");
                return new ResponseEntity<>(response,
                        HttpStatus.BAD_REQUEST);
            }
			questionService.changePermissionStatus(questionId);
			ResponseEnterDTO response=new ResponseEnterDTO(true, "Успешно!");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            ResponseEnterDTO response=new ResponseEnterDTO(false, "Произошла ошибка");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
    }
    
}
