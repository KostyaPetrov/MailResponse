package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;

@Data
@AllArgsConstructor
public class ResponseQuestionDTO {
    private List<Question> question;
    private String inform;
}
