package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Email;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.EmailRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;


@RequiredArgsConstructor
public class RegisterUserDelegate implements JavaDelegate{
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private EmailRepository emailRepository;

    @Transactional 
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String userName = (String) delegateExecution.getVariable("userName");
        String password = (String) delegateExecution.getVariable("password");
        String email = (String) delegateExecution.getVariable("email");
        
        if (email == null || email.isEmpty()) {
            throw new Exception("Email is required");
        }


        try {
            User user = new User();
            user.setName(userName);
            user.setRole(Roles.USER);
            user.setBlockStatus(BlockStatus.FREE);
            user.setPassword(passwordEncoder.encode(password));

            userRepository.save(user);

            Email emailObj = new Email();
            emailObj.setEmail(email);
            emailObj.setUser(user);

            emailRepository.save(emailObj);

        } catch (Exception e) {
            // Логирование ошибки
            System.out.println("Error registering user: " + e.getMessage());
            throw new Exception("Failed to register user", e);
        }
    }


}
