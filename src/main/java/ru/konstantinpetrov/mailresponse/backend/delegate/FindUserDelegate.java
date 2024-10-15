package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseUserDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;


@RequiredArgsConstructor
public class FindUserDelegate implements JavaDelegate {
    private UserService userService;
    private QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String name = (String) delegateExecution.getVariable("name");

        try {
            System.out.println("User Controller get name " + name +" for find information");
			User user = this.userService.findUser(name);
            List<Long> listQuestionId = this.questionService.getIdQuestion(user.getUserId());   

            delegateExecution.setVariable("userId", user.getUserId());
            delegateExecution.setVariable("listQuestionId", listQuestionId);
			
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
    }


}