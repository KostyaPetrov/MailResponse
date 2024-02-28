package ru.konstantinpetrov.mailresponse.backend.entity;

import javax.persistence.*;
import lombok.*;



@Data
@RequiredArgsConstructor
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String textQuestion;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private Integer countReview;
}
