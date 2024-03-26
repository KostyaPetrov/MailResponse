package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;

public interface QuestionService {
    List<Question> getQuestion();

    List<Long> getIdQuestion(long userId);

    void deleteQuestionById(long id);

    void deleteQuestionByText(String text);

    void deleteQuestionByUserId(Long userId);

    void deleteQuestionByPermissionStatus(PermissionStatus permissionStatus);

    void changePermissionStatus(Long questionId);
}
