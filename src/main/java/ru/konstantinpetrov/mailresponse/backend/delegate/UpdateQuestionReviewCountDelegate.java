package ru.konstantinpetrov.mailresponse.backend.delegate;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;
import ru.konstantinpetrov.mailresponse.backend.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class UpdateQuestionReviewCountDelegate implements JavaDelegate {

    private static final Logger logger = LoggerFactory.getLogger(UpdateQuestionReviewCountDelegate.class);

    private final QuestionRepository questionRepository;

    @Autowired
    public UpdateQuestionReviewCountDelegate(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long questionId = (Long) delegateExecution.getVariable("questionId");
        logger.info("Updating review count for question with ID: {}", questionId);

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        question.setCountReview(question.getCountReview() + 1);
        questionRepository.save(question);

        logger.info("Review count updated successfully for question with ID: {}", questionId);
    }
}

