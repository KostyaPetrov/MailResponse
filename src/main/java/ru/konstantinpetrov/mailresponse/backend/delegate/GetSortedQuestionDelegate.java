package ru.konstantinpetrov.mailresponse.backend.delegate;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.security.access.prepost.PreAuthorize;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.QuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.service.QuestionService;


@Component
@RequiredArgsConstructor
public class GetSortedQuestionDelegate implements JavaDelegate{
    private final QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try {
            List<Question> sortedListQuestions = questionService.getQuestion();
            sortedListQuestions.sort(Comparator.comparing(Question::getCountReview).reversed());

            // Преобразуем список Question в список QuestionDTO
            List<QuestionDTO> sortedListQuestionDTOs = sortedListQuestions.stream()
                    .map(question -> {
                        QuestionDTO dto = new QuestionDTO();
                        dto.setId(question.getId());
                        dto.setTextQuestion(question.getTextQuestion());
                        dto.setUserId(question.getUserId());
                        dto.setCountReview(question.getCountReview());
                        dto.setPermissionStatus(question.getPermissionStatus());
                        return dto;
                    })
                    .collect(Collectors.toList());

            delegateExecution.setVariable("sortedListQuestions", sortedListQuestionDTOs);

            String successMessage = "Список вопросов успешно отсортирован по количеству отзывов.";
            delegateExecution.setVariable("operationMessage", successMessage);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }


}
