package ru.konstantinpetrov.mailresponse.backend.delegate;

import camundajar.impl.scala.util.Try;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Mark;
import ru.konstantinpetrov.mailresponse.backend.service.MarkService;
@Component
@RequiredArgsConstructor
public class CreateMarkDelegate implements JavaDelegate {

    private final MarkService markService;

    @Transactional
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        Long userId = (Long) delegateExecution.getVariable("userId");
        Integer questionId = (Integer) delegateExecution.getVariable("questionId");
        Integer grade = (Integer) delegateExecution.getVariable("grade");

        Mark mark = new Mark();
        mark.setQuestionId(questionId);
        mark.setGrade(grade);
        try {
            markService.addMark(mark);
        }catch(Exception e){
            throw new Exception("У пользователь с ID " + userId + " был сброшен email.");
        }
        String successMessage = "Оценка " + grade + " для вопроса с ID " + questionId + " успешно добавлена.";
        delegateExecution.setVariable("operationMessage", successMessage);
    }


}
