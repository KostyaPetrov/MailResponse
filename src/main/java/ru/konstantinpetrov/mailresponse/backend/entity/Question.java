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

    @Column(name = "text_question", nullable = false)
    private String textQuestion;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "count_review", nullable = false)
    private Integer countReview;

    @Column(name="permission_status", nullable = false)
    private PermissionStatus permissionStatus;
}
