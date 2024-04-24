package ru.konstantinpetrov.mailresponse.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.konstantinpetrov.mailresponse.backend.entity.ReviewQuestion;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewQuestion, Long> {
    // Optional<ReviewQuestion> findAllByQuestionId(Long id);
    // List<ReviewQuestion> findAllByQuestionId(Long id);
    List<ReviewQuestion> findByQuestionId(Long id);
}
