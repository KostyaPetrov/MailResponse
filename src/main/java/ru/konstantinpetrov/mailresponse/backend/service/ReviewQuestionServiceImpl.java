package ru.konstantinpetrov.mailresponse.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.repository.ReviewRepository;

@Service
public class ReviewQuestionServiceImpl implements ReviewQuestionService{

    private ReviewRepository reviewRepository;

    @Autowired
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }


    @Override
    public List<ReviewQuestion> getReview(Long id){
        // System.out.println("In getReview method");
        // this.reviewRepository.findByQuestionId(id);
        // System.out.println("Test get responce");

        List<ReviewQuestion> listReview = this.reviewRepository.findByQuestionId(id);
        // System.out.println("Test save responce");

        return listReview;
        
    }

    @Override
    @Transactional
    public void addReview(ReviewQuestion reviewQuestion){
        try{
            this.reviewRepository.save(reviewQuestion);
        }catch(Exception exception){
            System.out.println("Error: " + exception);
            exception.printStackTrace();
        }   
    }

    @Override
    public void deleteResponseByQuestionId(Long id){
        try{
            this.reviewRepository.deleteAllById(id);
        }catch(Exception e){
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }
}
