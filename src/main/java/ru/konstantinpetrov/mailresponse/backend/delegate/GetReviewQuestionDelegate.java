package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseReviewQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;


@Component
@RequiredArgsConstructor
public class GetReviewQuestionDelegate implements JavaDelegate{
    private final ReviewQuestionService reviewQuestionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long reviewId = (Long) delegateExecution.getVariable("reviewId");
        
        
        try {
			List<ReviewQuestion> responseList = reviewQuestionService.getReview(reviewId);
			delegateExecution.setVariable("listReview", responseList);
           
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }



}
