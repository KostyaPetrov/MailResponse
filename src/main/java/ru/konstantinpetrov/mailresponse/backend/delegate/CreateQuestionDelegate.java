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
    public void execute(DelegateExecution delegateExecution){
        String textQuestion = (String) delegateExecution.getVariable("textQuestion");
        long userId = (long) delegateExecution.getVariable("userId");
        Integer countReview = 0;
        // PermissionStatus permissionStatus;


        Question question = new Question();
        question.setUserId(userId);
        question.setCountReview(countReview);
        question.setPermissionStatus(PermissionStatus.OPEN);
        question.setTextQuestion(textQuestion);
        
        questionServiceImpl.addQuestion(question);
    }

    
}
