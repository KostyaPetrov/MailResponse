package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;

@Service
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

    @Override
    public List<Long> getIdQuestion(long userId){
        List<Question> listQuestion = this.questionRepository.getAllByUserId(userId);
        List<Long> returnList=new ArrayList<>();
        Question currentQuestion;
        for(int i=0; i<listQuestion.size(); i++){
            currentQuestion=listQuestion.get(i);
            returnList.add(currentQuestion.getId());
        }
        return returnList;
    }

}
