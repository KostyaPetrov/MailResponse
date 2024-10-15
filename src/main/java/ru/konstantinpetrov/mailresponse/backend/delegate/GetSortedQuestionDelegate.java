package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.Comparator;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;



@RequiredArgsConstructor
public class GetSortedQuestionDelegate implements JavaDelegate{
    private QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try{
            List<Question> sortedListQuestions = questionService.getQuestion();
            sortedListQuestions.sort(Comparator.comparing(Question::getCountReview).reversed());
            delegateExecution.setVariable("sortedListQuestions", sortedListQuestions);
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
    }


}
