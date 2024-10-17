package ru.konstantinpetrov.mailresponse.backend.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ru.konstantinpetrov.mailresponse.backend.entity.Mark;
import ru.konstantinpetrov.mailresponse.backend.service.MarkService;

@RequiredArgsConstructor
public class CreateMarkDelegate implements JavaDelegate {

    private MarkService markService;

    @Transactional
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        
        Integer questionId = (Integer) delegateExecution.getVariable("questionId");
        Integer grade = (Integer) delegateExecution.getVariable("grade");

        Mark mark = new Mark();
        mark.setQuestionId(questionId);
        mark.setGrade(grade);
        
        markService.addMark(mark);
        
    }


}
