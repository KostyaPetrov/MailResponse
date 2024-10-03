package ru.konstantinpetrov.mailresponse.backend.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

@Service
public class KafkaConsumerService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumerService.class);
    @KafkaListener(topics = "question-topic", groupId = "group_id")
    public void listenQuestionTopic(String message) {
        logger.info("Received message on question-topic: {}", message);
        Long questionId = extractIdFromMessage(message);

        // Обновляем количество отзывов для вопроса
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        question.setCountReview(question.getCountReview() + 1);
        logger.info("Updating count_review for question with ID: {}", questionId);
        questionRepository.save(question);
    }

    @KafkaListener(topics = "user-topic", groupId = "group_id")
    public void listenUserTopic(String message) {
        logger.info("Received message on user-topic: {}", message);
        Long userId = extractIdFromMessage(message);

        // Обновляем количество ответов у пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setAnswerCount(user.getAnswerCount() + 1);
        userRepository.save(user);
        logger.info("Updated answerCount for user with ID: {}", userId);
    }

    private Long extractIdFromMessage(String message) {
        // Логика извлечения ID из сообщения
        // Например, если сообщение в формате "Answer added to question with ID: 1"
        return Long.parseLong(message.split(": ")[1]);
    }
}