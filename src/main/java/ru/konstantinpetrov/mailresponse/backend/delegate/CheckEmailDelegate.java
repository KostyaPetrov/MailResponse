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
        Long userId = (Long) delegateExecution.getVariable("userId"); // Получаем userId из переменных процесса

        // Используем исправленный метод existsByUser_UserId
        boolean emailExistsForUser = emailRepository.existsByUser_UserId(userId);
        delegateExecution.setVariable("emailExists", emailExistsForUser);

        if (!emailExistsForUser) {
            // Если email не существует для данного пользователя, выбрасываем исключение или обрабатываем ситуацию
            throw new Exception("У пользователя с ID " + userId + " нет связанного email.");
        }

        String successMessage = "Email существует для пользователя с ID " + userId + ".";
        delegateExecution.setVariable("operationMessage", successMessage);
    }
}