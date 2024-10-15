package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;


@RequiredArgsConstructor
public class GetQuestionDelegate implements JavaDelegate {
    private QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try{
            List<Question> listQuestions = questionService.getQuestion();

            delegateExecution.setVariable("listQuestions", listQuestions);
        }catch(Exception e){
            System.out.println("Error: " + e);

        }

    }


}
