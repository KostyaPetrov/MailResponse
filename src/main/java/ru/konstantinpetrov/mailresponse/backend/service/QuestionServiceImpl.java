package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
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

    @Override
    @Transactional
    public void deleteQuestionById(long id){
        try{
            this.questionRepository.deleteById(id);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteQuestionByText(String text){
        try{
            this.questionRepository.deleteByTextQuestion(text);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void deleteQuestionByUserId(Long userId){
        try{
            this.questionRepository.deleteAllByUserId(userId);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public void deleteQuestionByPermissionStatus(PermissionStatus permissionStatus){
        try{
            this.questionRepository.deleteAllByPermissionStatus(permissionStatus);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void changePermissionStatus(Long questionId){
        try{
            Question question = questionRepository.getById(questionId);
            if(question.getPermissionStatus()==PermissionStatus.OPEN){
                question.setPermissionStatus(PermissionStatus.CLOSE);
            }else{
                question.setPermissionStatus(PermissionStatus.OPEN);
            }

            questionRepository.save(question);
            
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public void addQuestion(Question question){
        try{
            this.questionRepository.save(question);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }   
    }
}
