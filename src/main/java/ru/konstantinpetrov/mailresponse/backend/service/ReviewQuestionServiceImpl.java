package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.ReviewRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

@Service
public class ReviewQuestionServiceImpl implements ReviewQuestionService{

    private ReviewRepository reviewRepository;
    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<ReviewQuestion> getReview(Long id){
        // System.out.println("In getReview method");
        // this.reviewRepository.findByQuestionId(id);
        // System.out.println("Test get responce");

        List<ReviewQuestion> listReview = this.reviewRepository.findByQuestionId(id);
        // System.out.println("Test save responce");

        return listReview;
        
    }

    @Override
    @Transactional
    public void addReview(Long questionId, Long userId, String textReview) {
        // Проверяем, что вопрос существует
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        // Создаем новый ответ
        ReviewQuestion reviewQuestion = new ReviewQuestion();
        reviewQuestion.setTextReview(textReview);
        reviewQuestion.setQuestionId(questionId);

        // Сохраняем ответ в базе данных
        reviewRepository.save(reviewQuestion);

        // Отправляем сообщения через Kafka (но больше не обновляем количество ответов здесь)
        kafkaProducerService.sendAnswerAddedToQuestionMessage(questionId);
        kafkaProducerService.sendAnswerAddedToUserMessage(userId);
    }


    @Override
    public void deleteResponseByQuestionId(Long id){
        try{
            this.reviewRepository.deleteAllById(id);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
