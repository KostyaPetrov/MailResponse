package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import ru.konstantinpetrov.mailresponse.backend.service.KafkaProducerService;

public class SendAnswerMessageDelegate implements JavaDelegate{
    private KafkaProducerService kafkaProducerService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long questionId = (Long) delegateExecution.getVariable("questionId");
        Long userId = (Long) delegateExecution.getVariable("userId");
        kafkaProducerService.sendAnswerAddedToQuestionMessage(questionId);
        kafkaProducerService.sendAnswerAddedToUserMessage(userId);

    }

}
