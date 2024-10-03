package ru.konstantinpetrov.mailresponse.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.EmailRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.ReviewRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

import java.util.List;

@Service
public class UserCleanupService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    // Запланированный метод, который будет выполняться каждый день в 00:00
    @Scheduled(cron = "0 0 0 * * ?") // Каждый день в полночь
    @Transactional
    public void removeBlockedUsers() {
        List<User> blockedUsers = userRepository.findAllByBlockStatus(BlockStatus.BLOCKED);
        for (User user : blockedUsers) {
            // Удалите все связанные email записи
            emailRepository.deleteByUser(user);

            // Удалите все вопросы, связанные с пользователем
            List<Long> questionIds = questionRepository.findAllByUserId(user.getUserId());
            for (Long questionId : questionIds) {
                // Удалите все отзывы, связанные с вопросом
                reviewRepository.deleteByQuestionId(questionId);
            }
            questionRepository.deleteByUserId(user.getUserId());
        }
        userRepository.deleteAll(blockedUsers);
        System.out.println("Blocked users removed: " + blockedUsers.size());
    }
}