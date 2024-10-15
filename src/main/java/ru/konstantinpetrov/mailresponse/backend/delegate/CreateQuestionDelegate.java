package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionServiceImpl;


@RequiredArgsConstructor
public class CreateQuestionDelegate implements JavaDelegate{
    private QuestionServiceImpl questionServiceImpl;

    @Override
    public void execute(DelegateExecution delegateExecution){
        String textQuestion = (String) delegateExecution.getVariable("textQuestion");
        long userId = (long) delegateExecution.getVariable("userId");
        Integer countReview = (Integer) delegateExecution.getVariable("countReview");
        PermissionStatus permissionStatus = (PermissionStatus) delegateExecution.getVariable("permissionStatus");


        Question question = new Question();
        question.setUserId(userId);
        question.setCountReview(countReview);
        question.setPermissionStatus(permissionStatus);
        question.setTextQuestion(textQuestion);
        
        questionServiceImpl.addQuestion(question);
    }

    
}
