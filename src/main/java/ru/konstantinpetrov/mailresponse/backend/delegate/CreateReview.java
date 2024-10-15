package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;


@RequiredArgsConstructor
public class CreateReview implements JavaDelegate {

    ReviewQuestionService reviewQuestionService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");
        Long questionId = (Long) delegateExecution.getVariable("questionId");
        String textReview = (String) delegateExecution.getVariable("textReview");

        try {
            // Вызываем сервис для добавления нового отзыва
            reviewQuestionService.addReview(questionId, userId, textReview);
            
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }




}
