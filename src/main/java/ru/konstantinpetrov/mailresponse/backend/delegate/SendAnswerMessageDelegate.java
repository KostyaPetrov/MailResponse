package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import org.springframework.stereotype.Component;
import ru.konstantinpetrov.mailresponse.backend.service.KafkaProducerService;
@Component
public class SendAnswerMessageDelegate implements JavaDelegate{
    private KafkaProducerService kafkaProducerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Integer questionId = (Integer) delegateExecution.getVariable("questionId");
        Integer userId = (Integer) delegateExecution.getVariable("userId");

        Long fieldUserId = Long.valueOf(userId);
        Long fieldQuestionId = Long.valueOf(questionId);

        kafkaProducerService.sendAnswerAddedToQuestionMessage(fieldQuestionId);
        kafkaProducerService.sendAnswerAddedToUserMessage(fieldUserId);

    }

}
