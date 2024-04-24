package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;

public interface ReviewQuestionService {
    List<ReviewQuestion> getReview(Long id);

    // void getReview(Long id);

    void addReview(ReviewQuestion reviewQuestion);
}
