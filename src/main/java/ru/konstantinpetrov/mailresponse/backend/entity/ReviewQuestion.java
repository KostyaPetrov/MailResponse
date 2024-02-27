package ru.konstantinpetrov.mailresponse.backend.entity;

import javax.persistence.*;
import lombok.*;



@Data
@RequiredArgsConstructor
@Entity
@Table(name = "review_question")
public class ReviewQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String textReview; 

    @Column(nullable = false)
    private Integer questionId;
    
}
