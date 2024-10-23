package ru.konstantinpetrov.mailresponse.backend.delegate;

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
public class GetQuestionDelegate implements JavaDelegate {
    private final QuestionService questionService;

    @PreAuthorize("hasRole('USER') or hasRole('MODERATOR')")
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        try{
            List<Question> listQuestions = questionService.getQuestion();
            List<QuestionDTO> listQuestionDTOs = listQuestions.stream()
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
            delegateExecution.setVariable("listQuestions", listQuestionDTOs);

            String successMessage = "Список вопросов успешно получен.";
            delegateExecution.setVariable("operationMessage", successMessage);
        }catch(Exception e){
            throw new Exception("Не получилось получить вопросы.");

        }

    }


}
