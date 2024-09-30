package ru.konstantinpetrov.mailresponse.backend.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.konstantinpetrov.mailresponse.backend.service.ReviewQuestionService;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewQuestionService reviewQuestionService;

    @PostMapping("/add")
    public ResponseEntity<String> addReview(@RequestParam Long questionId,
                                            @RequestParam Long userId,
                                            @RequestParam String textReview) {
        try {
            // Вызываем сервис для добавления нового отзыва
            reviewQuestionService.addReview(questionId, userId, textReview);
            return new ResponseEntity<>("Review added successfully", HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}