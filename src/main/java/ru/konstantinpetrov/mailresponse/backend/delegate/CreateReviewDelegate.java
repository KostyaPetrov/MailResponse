package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;

@Component
@RequiredArgsConstructor
public class CreateReviewDelegate implements JavaDelegate {

    private final ReviewQuestionService reviewQuestionService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer userId = (Integer) delegateExecution.getVariable("userId");
        Integer questionId = (Integer) delegateExecution.getVariable("questionId");
        String textReview = (String) delegateExecution.getVariable("textReview");

        Long fieldUserId = Long.valueOf(userId);
        Long fieldQuestionId = Long.valueOf(questionId);

        try {
            reviewQuestionService.addReview(fieldQuestionId, fieldUserId, textReview);
            String successMessage = "Отзыв от пользователя с ID " + userId + " для вопроса с ID " + questionId + " успешно добавлен.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            throw new Exception("У пользователь с ID " + userId + " не получилось отправить ответ.");
        }
    }




}
