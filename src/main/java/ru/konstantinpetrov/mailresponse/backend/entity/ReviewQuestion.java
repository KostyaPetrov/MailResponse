package ru.konstantinpetrov.mailresponse.backend.entity;

import javax.persistence.*;
import lombok.*;



@Data
@RequiredArgsConstructor
@Entity
@Table(name = "review_questions")
public class ReviewQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="text_review", nullable = false)
    private String textReview; 

    @Column(name="question_id", nullable = false)
    private Long questionId;
    
}
