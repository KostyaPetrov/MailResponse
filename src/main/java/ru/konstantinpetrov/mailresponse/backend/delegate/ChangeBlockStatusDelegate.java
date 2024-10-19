package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class ChangeBlockStatusDelegate implements JavaDelegate{
    private final UserService userService;


    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long changerId = (Long) delegateExecution.getVariable("changerId");
        Long userId = (Long) delegateExecution.getVariable("userId");

        try {
            System.out.println(changerId + " " + userId);
            Roles role=userService.getUserRole(changerId);
            if(role!=Roles.MODERATOR){
                throw new Exception("Не достаточно прав");    
            }
			userService.changeBlockStatus(userId);

        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
        
    }



}
