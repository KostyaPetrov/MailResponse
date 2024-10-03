package ru.konstantinpetrov.mailresponse.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.konstantinpetrov.mailresponse.backend.entity.PermissionStatus;
import ru.konstantinpetrov.mailresponse.backend.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> getAllByUserId(long userId);

    void deleteByTextQuestion(String text);

    void deleteAllByUserId(long userId);

    void deleteAllByPermissionStatus(PermissionStatus permissionStatus);
    void deleteByUserId(Long userId);

    List<Long> findAllByUserId(long userId);
}
