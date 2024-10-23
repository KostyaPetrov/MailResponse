package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionServiceImpl;

@Component
@RequiredArgsConstructor
public class CreateQuestionDelegate implements JavaDelegate{
    private final QuestionServiceImpl questionServiceImpl;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String textQuestion = (String) delegateExecution.getVariable("textQuestion");
        Integer userId = (Integer) delegateExecution.getVariable("userId");
        Integer countReview = 0;
        // PermissionStatus permissionStatus;
        Long realUserId = Long.valueOf(userId);

        Question question = new Question();
        question.setUserId(realUserId);
        question.setCountReview(countReview);
        question.setPermissionStatus(PermissionStatus.OPEN);
        question.setTextQuestion(textQuestion);
        try {
        questionServiceImpl.addQuestion(question);
        }catch(Exception e){
            throw new Exception("У пользователь с ID " + userId + " не получилось отправить вопрос.");
        }
        String successMessage = "Вопрос от пользователя с ID " + userId + " успешно создан.";
        delegateExecution.setVariable("operationMessage", successMessage);
    }

    
}
