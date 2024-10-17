package ru.konstantinpetrov.mailresponse.backend.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.repository.EmailRepository;

@Component
public class CheckEmailDelegate implements JavaDelegate {

    private final EmailRepository emailRepository;

    @Autowired
    public CheckEmailDelegate(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String email = (String) delegateExecution.getVariable("email");

        boolean emailExists = emailRepository.existsByEmail(email);
        delegateExecution.setVariable("emailExists", emailExists);

        if (emailExists) {
            throw new Exception("Email already exists: " + email);
        }
    }
}