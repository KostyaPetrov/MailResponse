package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.repository.ReviewRepository;

public class ReviewQuestionServiceImpl implements ReviewQuestionService{

    private ReviewRepository reviewRepository;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<ReviewQuestion> getReview(Long id){

        List<ReviewQuestion> listReview = this.reviewRepository.findAllByQuestionId(id);
        
        return listReview;
    }

    @Override
    public void addReview(ReviewQuestion reviewQuestion){
        try{
            this.reviewRepository.save(reviewQuestion);
        }catch(Exception exception){
            throw new IllegalArgumentException("An error occurred while saving the response");
        }   
    }
}
