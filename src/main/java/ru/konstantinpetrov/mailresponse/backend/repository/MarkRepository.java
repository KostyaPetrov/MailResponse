package ru.konstantinpetrov.mailresponse.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.konstantinpetrov.mailresponse.backend.entity.Mark;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    
}
