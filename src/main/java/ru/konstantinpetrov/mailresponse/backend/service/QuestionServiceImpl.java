package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;

public class QuestionServiceImpl implements QuestionService {

    private QuestionRepository questionRepository;

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }


    @Override
    public List<Question> getQuestion(){

        List<Question> listQuestion = this.questionRepository.findAll();

        return listQuestion;
    }
}
