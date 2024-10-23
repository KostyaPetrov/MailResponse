package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseUserDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@Component
@RequiredArgsConstructor
public class FindUserDelegate implements JavaDelegate {
    private final UserService userService;
    private final QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String name = (String) delegateExecution.getVariable("name");

        try {
			User user = this.userService.findUser(name);
            List<Long> listQuestionId = this.questionService.getIdQuestion(user.getUserId());   

            delegateExecution.setVariable("userId", user.getUserId());
            delegateExecution.setVariable("listQuestionId", listQuestionId);
            String successMessage = "Пользователь " + name + " успешно найден.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            throw new Exception("Пользователя  " + name + " не получилось найти.");
            
        }
    }


}
