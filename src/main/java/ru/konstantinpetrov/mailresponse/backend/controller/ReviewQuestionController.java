package ru.konstantinpetrov.mailresponse.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseEnterDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ResponseReviewQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.dtoLayer.ReviewQuestionDTO;
import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;
import ru.konstantinpetrov.mailresponse.backend.entity.Roles;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;
import ru.konstantinpetrov.mailresponse.backend.service.UserService;

@RestController
public class ReviewQuestionController {
    private ReviewQuestionService reviewQuestionService;

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Autowired
    public void setReviewQuestionService(ReviewQuestionService reviewQuestionService) {
        this.reviewQuestionService = reviewQuestionService;
    }


    @GetMapping(path="/getResponse/{id}")
	public ResponseEntity<ResponseReviewQuestionDTO> getReviewQuestion(@PathVariable Long id) {
		try {
            // System.out.println("get id:");
            // System.out.println(id);
			List<ReviewQuestion> responseList = reviewQuestionService.getReview(id);
			ResponseReviewQuestionDTO response=new ResponseReviewQuestionDTO(responseList, "Response succes getted");
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
           
        } catch (Exception e) {
            ResponseReviewQuestionDTO response=new ResponseReviewQuestionDTO(null, "Error receiving response");
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);

        }
	}

    @PostMapping(path="/addResponse")
	public ResponseEntity<ResponseEnterDTO> getReviewQuestion(@RequestBody ReviewQuestionDTO reviewQuestionDTO) {
		try {
            ReviewQuestion reviewQuestion=new ReviewQuestion();
            reviewQuestion.setTextReview(reviewQuestionDTO.getTextReview());
            reviewQuestion.setQuestionId(reviewQuestionDTO.getQuestionId());
			reviewQuestionService.addReview(reviewQuestion);
			ResponseEnterDTO response=new ResponseEnterDTO(true);
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            ResponseEnterDTO response=new ResponseEnterDTO(false);
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}

    @CrossOrigin
    @DeleteMapping(path="/deleteResponse/{userId}/{responseId}")
    public ResponseEntity<ResponseEnterDTO> deleteResponseById(@PathVariable Long userId, @PathVariable Long responseId) {
		try {

            Roles role=userService.getUserRole(userId);
            
            if(role!=Roles.MODERATOR){
                throw new Exception();    
            }

		    reviewQuestionService.deleteResponseByQuestionId(responseId);
			ResponseEnterDTO response=new ResponseEnterDTO(true);
			return new ResponseEntity<>(response,
                 HttpStatus.OK);
        } catch (Exception e) {
            ResponseEnterDTO response=new ResponseEnterDTO(false);
            return new ResponseEntity<>(response,
                    HttpStatus.BAD_REQUEST);
        }
	}
}
