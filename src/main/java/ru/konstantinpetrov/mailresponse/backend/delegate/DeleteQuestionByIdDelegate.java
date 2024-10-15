package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;


@RequiredArgsConstructor
public class DeleteQuestionByIdDelegate implements JavaDelegate {

    private UserService userService;
    QuestionService questionService;

    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        
        Long userId = (Long) delegateExecution.getVariable("userId");
        Long questionId = (Long) delegateExecution.getVariable("questionId");
        try {
            
            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                throw new Exception("Права на удаление имеет только модератор");
            }
			questionService.deleteQuestionById(questionId);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        
    }


}