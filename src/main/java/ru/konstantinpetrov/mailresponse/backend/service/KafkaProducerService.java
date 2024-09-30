package ru.konstantinpetrov.mailresponse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String QUESTION_TOPIC = "question-topic";
    private static final String USER_TOPIC = "user-topic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendAnswerAddedToQuestionMessage(Long questionId) {
        String message = "Answer added to question with ID: " + questionId;
        kafkaTemplate.send(QUESTION_TOPIC, message);
    }

    public void sendAnswerAddedToUserMessage(Long userId) {
        String message = "Answer added to user with ID: " + userId;
        kafkaTemplate.send(USER_TOPIC, message);
    }
}