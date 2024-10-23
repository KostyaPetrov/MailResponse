package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseReviewQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ReviewQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;


@Component
@RequiredArgsConstructor
public class GetReviewQuestionDelegate implements JavaDelegate{
    private final ReviewQuestionService reviewQuestionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer reviewId = (Integer) delegateExecution.getVariable("reviewId");
        try {
            Long fieldReviewId = Long.valueOf(reviewId);
            List<ReviewQuestion> responseList = reviewQuestionService.getReview(fieldReviewId);

            // Преобразуем список ReviewQuestion в список ReviewQuestionDTO
            List<ReviewQuestionDTO> listReviewDTO = responseList.stream()
                    .map(reviewQuestion -> {
                        ReviewQuestionDTO dto = new ReviewQuestionDTO();
                        dto.setTextReview(reviewQuestion.getTextReview());
                        dto.setQuestionId(reviewQuestion.getQuestionId());
                        dto.setUserId(reviewQuestion.getId());
                        return dto;
                    })
                    .collect(Collectors.toList());

            delegateExecution.setVariable("listReview", listReviewDTO);

            String successMessage = "Отзывы для вопроса с ID " + reviewId + " успешно получены.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            throw new Exception("Не получилось получить отзовы.");
        }
    }



}
