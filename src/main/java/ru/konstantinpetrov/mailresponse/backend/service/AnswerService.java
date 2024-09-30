package ru.konstantinpetrov.mailresponse.backend.service;

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
public class AnswerService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;
    @Autowired
    private ReviewRepository reviewRepository;
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

        // Обновляем количество ответов у пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setAnswerCount(user.getAnswerCount() + 1);
        userRepository.save(user);

        // Отправляем сообщения через Kafka
        kafkaProducerService.sendAnswerAddedToQuestionMessage(questionId);
        kafkaProducerService.sendAnswerAddedToUserMessage(userId);
    }
}