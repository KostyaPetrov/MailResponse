package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;


@RequiredArgsConstructor
public class CreateUser implements JavaDelegate{
    private PasswordEncoder passwordEncoder;
    private UserService userService;

    @Transactional
    @PreAuthorize("hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userName = (String) delegateExecution.getVariable("userName");
        String password = (String) delegateExecution.getVariable("password");
        Boolean isModerator = (Boolean) delegateExecution.getVariable("isModerator");
        
        try {
            User user= new User();
            user.setName(userName);
            user.setPassword(passwordEncoder.encode(password));
            user.setAnswerCount(0);
            if(isModerator==true){
                user.setRole(Roles.MODERATOR);    
            }else{
                user.setRole(Roles.USER);
            }
            user.setBlockStatus(BlockStatus.FREE);
			userService.addUser(user);
			
        } catch (Exception e) {
            System.out.println("Error: " + e);
            
        }
    }


}
