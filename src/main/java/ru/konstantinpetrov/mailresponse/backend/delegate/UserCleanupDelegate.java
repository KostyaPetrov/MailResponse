package ru.konstantinpetrov.mailresponse.backend.delegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.entity.BlockStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.User;
import ru.konstantinpetrov.mailresponse.backend.repository.EmailRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.ReviewRepository;
import ru.konstantinpetrov.mailresponse.backend.repository.UserRepository;

import java.util.List;

@Component
public class UserCleanupDelegate implements JavaDelegate {

    private final UserRepository userRepository;
    private final EmailRepository emailRepository;
    private final QuestionRepository questionRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    public UserCleanupDelegate(UserRepository userRepository,
                               EmailRepository emailRepository,
                               QuestionRepository questionRepository,
                               ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.emailRepository = emailRepository;
        this.questionRepository = questionRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    @Transactional
    public void execute(DelegateExecution delegateExecution) throws Exception {
        List<User> blockedUsers = userRepository.findAllByBlockStatus(BlockStatus.BLOCKED);
        for (User user : blockedUsers) {
            // Удаление всех email записей, связанных с пользователем
            emailRepository.deleteByUser(user);

            // Удаление всех вопросов и отзывов, связанных с пользователем
            List<Long> questionIds = questionRepository.findAllByUserId(user.getUserId());
            for (Long questionId : questionIds) {
                reviewRepository.deleteByQuestionId(questionId);
            }
            questionRepository.deleteByUserId(user.getUserId());
        }

        // Удаление всех заблокированных пользователей
        userRepository.deleteAll(blockedUsers);

        System.out.println("Blocked users removed: " + blockedUsers.size());

        // Установка переменной в контексте Camunda для дальнейшего использования
        delegateExecution.setVariable("blockedUsersCount", blockedUsers.size());
    }
}