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
public class DeleteQuestionByIdDelegate implements JavaDelegate {

    private final UserService userService;
    private final QuestionService questionService;

    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        
        Integer userId = (Integer) delegateExecution.getVariable("userId");
        Integer questionId = (Integer) delegateExecution.getVariable("questionId");

        Long fieldUserId = Long.valueOf(userId);
        Long fieldQuestionId = Long.valueOf(questionId);


        try {
            
            Roles role=userService.getUserRole(fieldUserId);
            if(role!=Roles.MODERATOR){
                throw new Exception("Права на удаление имеет только модератор");
            }
			questionService.deleteQuestionById(fieldQuestionId);
            String successMessage = "Вопрос с ID " + questionId + " успешно удален.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            throw new Exception("У пользователь с ID " + userId + " не получилось удалить вопрос.");
        }

        
    }


}
