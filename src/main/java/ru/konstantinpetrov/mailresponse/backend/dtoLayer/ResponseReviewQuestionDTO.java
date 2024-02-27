package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;

@Data
@AllArgsConstructor
public class ResponseReviewQuestionDTO {
    private List<ReviewQuestion> reviewQuestion;
    private String inform;
}
