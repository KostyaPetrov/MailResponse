package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;


@RequiredArgsConstructor
public class DeleteResponseByIdDelegate implements JavaDelegate {
    private UserService userService;
    private ReviewQuestionService reviewQuestionService;


    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");
        Long responseId = (Long) delegateExecution.getVariable("responseId");

        try {
            Roles role=userService.getUserRole(userId);
            
            if(role!=Roles.MODERATOR){
                throw new Exception("Нет прав на это действие");    
            }

		    reviewQuestionService.deleteResponseByQuestionId(responseId);
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
        
    }


}
