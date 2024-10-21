package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class DeleteResponseByIdDelegate implements JavaDelegate {
    private final UserService userService;
    private final ReviewQuestionService reviewQuestionService;


    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer userId = (Integer) delegateExecution.getVariable("userId");
        Integer responseId = (Integer) delegateExecution.getVariable("responseId");

        Long fieldUserId = Long.valueOf(userId);
        Long fieldResponseId = Long.valueOf(responseId);

        try {
            Roles role=userService.getUserRole(fieldUserId);
            
            if(role!=Roles.MODERATOR){
                throw new Exception("Нет прав на это действие");    
            }

		    reviewQuestionService.deleteResponseByQuestionId(fieldResponseId);
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
        
    }


}
