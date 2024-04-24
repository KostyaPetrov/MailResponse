package ru.konstantinpetrov.mailresponse.backend.dtoLayer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewQuestionDTO {
    private String textReview; 

    private Long questionId;
}
