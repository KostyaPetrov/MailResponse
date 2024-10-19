package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class ChangePermissionStatusDelegate implements JavaDelegate{
    private final UserService userService;
    private final QuestionService questionService;

    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        try {
            Long userId=(Long) delegateExecution.getVariable("userId");
            Long questionId = (Long) delegateExecution.getVariable("questionId");

            Roles role=userService.getUserRole(userId);
            if(role!=Roles.MODERATOR){
                throw new Exception("Права на изменение статуса доступа имеет только модератор");
            }
			questionService.changePermissionStatus(questionId);
			
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }    
        
    }

}
