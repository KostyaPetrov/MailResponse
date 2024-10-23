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
        Integer changerId = (Integer) delegateExecution.getVariable("changerId");
        Integer userId = (Integer) delegateExecution.getVariable("userId");

        Long fieldChangerId = Long.valueOf(changerId);
        Long fieldUserId = Long.valueOf(userId);

        try {
            System.out.println(changerId + " " + userId);
            Roles role=userService.getUserRole(fieldChangerId);
            if(role!=Roles.MODERATOR){
                throw new Exception("Не достаточно прав");    
            }
			userService.changeBlockStatus(fieldUserId);
            String successMessage = "Статус блокировки пользователя с ID " + userId + " успешно изменен.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
        
    }



}
