package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import ru.konstantinpetrov.mailresponse.backend.entity.Question;

public interface QuestionService {
    List<Question> getQuestion();

    List<Long> getIdQuestion(long userId);
}
