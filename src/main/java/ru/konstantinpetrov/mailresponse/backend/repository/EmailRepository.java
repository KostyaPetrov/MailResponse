package ru.konstantinpetrov.mailresponse.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.konstantinpetrov.mailresponse.backend.entity.Email;
import ru.konstantinpetrov.mailresponse.backend.entity.User;

@Repository
public interface EmailRepository extends JpaRepository<Email, Long> {
    User findByEmail(String email);
    Email findByUser(User user);

    void deleteByUser(User user);
}